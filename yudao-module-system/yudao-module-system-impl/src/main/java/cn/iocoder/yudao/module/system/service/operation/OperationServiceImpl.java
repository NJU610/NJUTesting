package cn.iocoder.yudao.module.system.service.operation;

import cn.iocoder.yudao.module.system.controller.admin.contract.vo.ContractRespVO;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.DelegationRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.dal.dataobject.operation.OperationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.operation.OperationMapper;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import cn.iocoder.yudao.module.system.service.flow.FlowService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.util.operation.OperationUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 流程操作 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class OperationServiceImpl implements OperationService {

    @Resource
    private OperationMapper operationMapper;

    @Resource
    private FlowService flowService;

    @Resource
    private DelegationService delegationService;

    @Resource
    private ContractService contractService;

    @Resource
    private AdminUserService userService;

    public void saveLog (Long FLowId, FlowStateEnum fromState, FlowStateEnum toState) {
        String template = OperationUtil.getTemplate(fromState, toState);

        FlowDO flowDO = flowService.getFlow(FLowId);
        Long operatorId = null;
        Date operatorTime = new Date();
        String remark = template;

        if (template.contains("delegation")) {
            DelegationRespVO delegation = delegationService.getDelegation(flowDO.getDelegationId());
            if (template.contains("Remark")) {
                remark = template.replace("${delegationRemark}", delegation.getRemark());
            }
            if (template.contains("${delegationCreatorName}")) {
                operatorId = delegation.getCreatorId();
                AdminUserDO adminUserDO= userService.getUser(operatorId);
                remark = remark.replace("${delegationCreatorName}", adminUserDO.getNickname());
            } else if (template.contains("${delegationAcceptorName}")) {
                operatorId = delegation.getAcceptorId();
                AdminUserDO adminUserDO= userService.getUser(operatorId);
                remark = remark.replace("${delegationAcceptorName}", adminUserDO.getNickname());
            }

        } else if (template.contains("contract")) {
            ContractDO contract = contractService.getContract(flowDO.getContractId());
            if (template.contains("Remark")) {
                remark = template.replace("${contractRemark}", contract.getRemark());
            }
            if (template.contains("${contractCreatorName}")) {
                operatorId = contract.getCreatorId();
                AdminUserDO adminUserDO= userService.getUser(operatorId);
                remark = remark.replace("${contractCreatorName}", adminUserDO.getNickname());
            } else if (template.contains("${contractAcceptorName}")) {
                operatorId = contract.getAcceptorId();
                AdminUserDO adminUserDO= userService.getUser(operatorId);
                remark = remark.replace("${contractAcceptorName}", adminUserDO.getNickname());
            }
        }

        OperationDO operationDO = OperationDO.builder()
                .operatorId(operatorId)
                .operateTime(operatorTime)
                .remark(remark)
                .flowId(FLowId)
                .build();

        operationMapper.insert(operationDO);
    }

}
