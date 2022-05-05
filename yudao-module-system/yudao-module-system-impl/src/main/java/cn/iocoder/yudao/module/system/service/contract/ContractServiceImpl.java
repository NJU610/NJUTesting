package cn.iocoder.yudao.module.system.service.contract;

import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowMapper;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.system.dal.mysql.contract.ContractMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
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
    private FlowMapper flowMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Override
    public Long createContract(ContractCreateReqVO createReqVO) {
        Long delegationId = createReqVO.getDelegationId();
        Long creatorId = createReqVO.getCreatorId();
        if (creatorId == null) {
            creatorId = SecurityFrameworkUtils.getLoginUserId();
        }
        return createContract(delegationId, creatorId);
    }

    @Override
    public Long createContract(Long delegationId, Long creatorId) {
        // 检验委托是否存在
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper
                .eqIfPresent("delegation_id", delegationId)
                .eqIfPresent("deleted", false);
        FlowDO flow = flowMapper.selectOne(queryWrapper);
        if (flow == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
        if(flow.getContractId() != null) {
            throw exception(CONTRACT_ALREADY_EXISTS);
        }
        if (!Objects.equals(flow.getState(), FlowStateEnum.MARKET_DEPT_GENERATE_CONTRACT.getState())) {
            throw exception(FLOW_STATE_ERROR);
        }
        // 创建合同
        ContractDO contract = ContractDO.builder()
                .creatorId(creatorId)
                .launchTime(new Date())
                .build();
        contractMapper.insert(contract);
        Long contractId = contract.getId();
        // 更新流程
        flow.setContractId(contractId);
        flowMapper.updateById(flow);

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
            contract.setTable4Id(tableMongoRepository.create("table5", saveReqVO.getData()));
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
        // 检验流程状态
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("contract_id", contractId)
                .eqIfPresent("deleted", false);
        FlowDO flow = flowMapper.selectOne(queryWrapper);
        if (!Objects.equals(flow.getState(), FlowStateEnum.MARKET_DEPT_GENERATE_CONTRACT.getState())) {
            throw exception(FLOW_STATE_ERROR);
        }
        // 更新流程
        flow.setState(FlowStateEnum.CUSTOMER_WRITE_CONTRACT.getState());
        flowMapper.updateById(flow);
    }

    @Override
    public void submitContractClient(ContractSubmitReqVO submitReqVO) {
        // 检验合同是否存在
        Long contractId = submitReqVO.getId();
        this.validateContractExists(contractId);
        // 检验流程状态
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("contract_id", contractId)
                .eqIfPresent("deleted", false);
        FlowDO flow = flowMapper.selectOne(queryWrapper);
        if (!Objects.equals(flow.getState(), FlowStateEnum.CUSTOMER_WRITE_CONTRACT.getState())) {
            throw exception(FLOW_STATE_ERROR);
        }
        // 更新流程
        flow.setState(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT.getState());
        flowMapper.updateById(flow);
    }

    private void createIfAbsent(ContractSaveTableReqVO saveReqVO) {
//        Long delegationId = saveReqVO.getDelegationId();
//        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
//        queryWrapper
//                .eqIfPresent("delegation_id", delegationId)
//                .eqIfPresent("deleted", false);
//        FlowDO flow = flowMapper.selectOne(queryWrapper);
//        if (flow == null) {
//            throw exception(DELEGATION_NOT_EXISTS);
//        }
//        if (flow.getContractId() == null) {
//            ContractDO contract = ContractDO.builder()
//                    .creatorId()
//                    .launchTime(new Date())
//                    .build();
//        }
    }

    @Override
    public void updateContract(ContractUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateContractExists(updateReqVO.getId());
        // 更新
        ContractDO updateObj = ContractConvert.INSTANCE.convert(updateReqVO);
        contractMapper.updateById(updateObj);
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
