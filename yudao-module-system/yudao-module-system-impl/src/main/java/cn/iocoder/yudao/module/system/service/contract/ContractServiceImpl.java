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
        // 校验状态
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
        // 校验合同是否存在
        Long contractId = saveReqVO.getContractId();
        ContractDO contract = this.validateContractExists(contractId);
        // 保存表单
        if (contract.getTable4Id() == null) {
            contract.setTable4Id(tableMongoRepository.create("table4", saveReqVO.getData()));
            contractMapper.updateById(contract);
        } else {
            tableMongoRepository.upsert("table4", contract.getTable4Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveContractTable5(ContractSaveTableReqVO saveReqVO) {
        // 校验合同是否存在
        Long contractId = saveReqVO.getContractId();
        ContractDO contract = this.validateContractExists(contractId);
        // 保存表单
        if (contract.getTable5Id() == null) {
            contract.setTable5Id(tableMongoRepository.create("table5", saveReqVO.getData()));
            contractMapper.updateById(contract);
        } else {
            tableMongoRepository.upsert("table5", contract.getTable5Id(), saveReqVO.getData());
        }
    }

    @Override
    public void submitContractStaff(ContractSubmitReqVO submitReqVO) {
        // 校验合同是否存在
        Long contractId = submitReqVO.getId();
        this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT,
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL);
        // 更新状态
        DelegationStateEnum oldState = DelegationStateEnum.getByState(delegation.getState());
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                oldState, DelegationStateEnum.CLIENT_AUDIT_CONTRACT,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 起草了合同，用户检查中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void submitContractClient(ContractSubmitReqVO submitReqVO) {
        // 校验合同是否存在
        Long contractId = submitReqVO.getId();
        this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CLIENT_WRITING_CONTRACT,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL);
        // 更新状态
        DelegationStateEnum oldEnum = DelegationStateEnum.getByState(delegation.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        String remark;
        assert oldEnum != null;
        if (Objects.equals(oldEnum.getState(), DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState())) {
            remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + "提交了合同，市场部审核中";
        } else {
            remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + "重新提交了合同，市场部审核中";
        }
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                oldEnum, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT,
                remark,
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void acceptContractClient(ContractAcceptReqVO acceptReqVO) {
        // 校验合同是否存在
        Long contractId = acceptReqVO.getId();
        this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT, DelegationStateEnum.CLIENT_WRITING_CONTRACT,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 接受合同草稿，填写合同中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void rejectContractClient(ContractRejectReqVO rejectReqVO) {
        // 校验合同是否存在
        Long contractId = rejectReqVO.getId();
        ContractDO contract = this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT);
        // 更新拒绝原因
        contract.setClientRemark(rejectReqVO.getReason());
        contractMapper.updateById(contract);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CLIENT_AUDIT_CONTRACT, DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 不接受合同，原因：" + contract.getClientRemark() + "。 市场部修改中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void rejectContractStaff(ContractRejectReqVO rejectReqVO) {
        // 校验合同是否存在
        Long contractId = rejectReqVO.getId();
        ContractDO contract = this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT);
        // 更新拒绝原因
        contract.setStaffRemark(rejectReqVO.getReason());
        contractMapper.updateById(contract);
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核合同不通过，原因：" + contract.getStaffRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void acceptContractStaff(ContractAcceptReqVO acceptReqVO) {
        // 校验合同是否存在
        Long contractId = acceptReqVO.getId();
        this.validateContractExists(contractId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT);
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.CONTRACT_SIGNING.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT, DelegationStateEnum.CONTRACT_SIGNING,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核合同通过",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void uploadDocument(@Valid ContractUploadDocReqVO uploadReqVO) {
        // 校验合同是否存在
        Long contractId = uploadReqVO.getId();
        this.validateContractExists(uploadReqVO.getId());
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByContract(contractId,
                DelegationStateEnum.CONTRACT_SIGNING);
        // 更新url
        ContractDO updateObj = ContractConvert.INSTANCE.convert(uploadReqVO);
        contractMapper.updateById(updateObj);
        // 更新状态
        delegation.setState(DelegationStateEnum.CONTRACT_SIGN_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CONTRACT_SIGNING, DelegationStateEnum.CONTRACT_SIGN_SUCCESS,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 已上传合同扫描件，合同签署成功",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});

        delegation.setState(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CONTRACT_SIGN_SUCCESS, DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO,
                "测试中心：等待用户上传样品中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("contract", contractMapper.selectById(contractId));}});
    }

    @Override
    public void deleteContract(Long id) {
        // 校验存在
        ContractDO contract = this.validateContractExists(id);
        // 删除表格
        if (contract.getTable4Id() != null) {
            tableMongoRepository.delete("table4", contract.getTable4Id());
        }
        if (contract.getTable5Id() != null) {
            tableMongoRepository.delete("table5", contract.getTable5Id());
        }
        // 删除
        contractMapper.deleteById(id);
    }

    private ContractDO validateContractExists(Long id) {
        ContractDO contract = contractMapper.selectById(id);
        if (contract == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
        return contract;
    }

    @Override
    public ContractDO getContract(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    public String getContractTable4(String id) {
        return tableMongoRepository.get("table4", id);
    }

    @Override
    public String getContractTable5(String id) {
        return tableMongoRepository.get("table5", id);
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
