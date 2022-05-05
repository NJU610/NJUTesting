package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.DelegationRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowLogMapper;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
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
public class FlowLogServiceImpl implements FlowLogService {

    @Resource
    private FlowLogMapper flowLogMapper;

    @Resource
    private FlowService flowService;

    @Resource
    private DelegationService delegationService;

    @Resource
    private ContractService contractService;

    @Resource
    private AdminUserService userService;



    @Override
    public void saveLog (Long flowId, FlowStateEnum fromState, FlowStateEnum toState) {
        String template = OperationUtil.getTemplate(fromState, toState);

        FlowDO flowDO = flowService.getFlow(flowId);
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

        FlowLogDO flowLogDO = FlowLogDO.builder()
                .operatorId(operatorId)
                .operateTime(operatorTime)
                .remark(remark)
                .flowId(flowId)
                .fromState(fromState.getState())
                .toState(toState.getState())
                .build();

        flowLogMapper.insert(flowLogDO);
    }

}
