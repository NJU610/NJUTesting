package cn.iocoder.yudao.module.system.service.contract;

import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.system.dal.mysql.contract.ContractMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 合同 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class ContractServiceImpl implements ContractService {

    @Resource
    private ContractMapper contractMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Resource
    @Lazy
    private FlowLogService flowLogService;

    @Resource
    private AdminUserService userService;

    @Override
    public Long createContract(ContractCreateReqVO createReqVO) {
        Long delegationId = createReqVO.getDelegationId();
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT);
        // 创建合同
        ContractDO contract = ContractDO.builder().build();
        contractMapper.insert(contract);
        Long contractId = contract.getId();
        // 更新委托
        delegation.setContractId(contractId);
        delegationMapper.updateById(delegation);
        return contractId;
    }

    @Override
    public void saveContractTable4(ContractSaveTableReqVO saveReqVO) {
        // 检验合同是否存在
        Long contractId = saveReqVO.getContractId();
        this.validateContractExists(contractId);

        ContractDO contract = contractMapper.selectById(contractId);
        if (contract.getTable4Id() == null) {
            contract.setTable4Id(tableMongoRepository.create("table4", saveReqVO.getData()));
        } else {
            tableMongoRepository.upsert("table4", contract.getTable4Id(), saveReqVO.getData());
        }

        contractMapper.updateById(contract);
    }

    @Override
    public void saveContractTable5(ContractSaveTableReqVO saveReqVO) {
        // 检验合同是否存在
        Long contractId = saveReqVO.getContractId();
        this.validateContractExists(contractId);

        ContractDO contract = contractMapper.selectById(contractId);
        if (contract.getTable5Id() == null) {
            contract.setTable5Id(tableMongoRepository.create("table5", saveReqVO.getData()));
        } else {
            tableMongoRepository.upsert("table5", contract.getTable5Id(), saveReqVO.getData());
        }

        contractMapper.updateById(contract);
    }

    @Override
    public void submitContractStaff(ContractSubmitReqVO submitReqVO) {
        // 检验合同是否存在
        Long contractId = submitReqVO.getId();
        this.validateContractExists(contractId);
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT, DelegationStateEnum.CLIENT_AUDIT_CONTRACT,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 起草了合同，用户检查中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void submitContractClient(ContractSubmitReqVO submitReqVO) {
        // 检验合同是否存在
        Long contractId = submitReqVO.getId();
        this.validateContractExists(contractId);
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
    }

    @Override
    public void rejectContractClient(ContractRejectReqVO rejectReqVO) {
        // 检验合同是否存在
        Long contractId = rejectReqVO.getId();
        this.validateContractExists(contractId);
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT);
        // 更新拒绝原因
        ContractDO contract = contractMapper.selectById(contractId);
        contract.setClientRemark(rejectReqVO.getReason());
        contractMapper.updateById(contract);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT, DelegationStateEnum.CLIENT_AUDIT_CONTRACT,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 不接受合同，市场部修改中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void rejectContractStaff(ContractRejectReqVO rejectReqVO) {
        // 检验合同是否存在
        Long contractId = rejectReqVO.getId();
        this.validateContractExists(contractId);
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT);
        // 更新拒绝原因
        ContractDO contract = contractMapper.selectById(contractId);
        contract.setStaffRemark(rejectReqVO.getReason());
        contractMapper.updateById(contract);
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL.getState());
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void acceptContractStaff(ContractAcceptReqVO acceptReqVO) {
        // 检验合同是否存在
        Long contractId = acceptReqVO.getId();
        this.validateContractExists(contractId);
        // 检验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT);
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.CONTRACT_SIGNING.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void uploadDocument(@Valid ContractUploadDocReqVO uploadReqVO) {
        // 校验存在
        this.validateContractExists(uploadReqVO.getId());
        // 校验状态
        DelegationDO delegation = delegationMapper.selectOne("contract_id", uploadReqVO.getId());
        if (delegation == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
        if (!Objects.equals(delegation.getState(),
                DelegationStateEnum.CONTRACT_SIGNING.getState())) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        // 更新url
        ContractDO updateObj = ContractConvert.INSTANCE.convert(uploadReqVO);
        contractMapper.updateById(updateObj);
        // 更新状态
        delegation.setState(DelegationStateEnum.CONTRACT_SIGN_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.CLIENT_SENDING_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void deleteContract(Long id) {
        // 校验存在
        this.validateContractExists(id);
        // 删除表格
        ContractDO contract = contractMapper.selectById(id);
        if (contract.getTable4Id() != null) {
            tableMongoRepository.delete("table4", contract.getTable4Id());
        }
        if (contract.getTable5Id() != null) {
            tableMongoRepository.delete("table5", contract.getTable5Id());
        }
        // 删除
        contractMapper.deleteById(id);
    }

    private void validateContractExists(Long id) {
        if (contractMapper.selectById(id) == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public ContractDO getContract(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    public List<ContractDO> getContractList(Collection<Long> ids) {
        return contractMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ContractDO> getContractPage(ContractPageReqVO pageReqVO) {
        return contractMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ContractDO> getContractList(ContractExportReqVO exportReqVO) {
        return contractMapper.selectList(exportReqVO);
    }

}
