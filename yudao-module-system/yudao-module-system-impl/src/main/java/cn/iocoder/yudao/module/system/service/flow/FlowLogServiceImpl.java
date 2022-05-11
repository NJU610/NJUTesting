package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowLogMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    private DelegationService delegationService;

    @Resource
    private ContractService contractService;

    @Resource
    private AdminUserService userService;

    public void saveLog (Long delegationId, Long operatorId,
                         DelegationStateEnum fromState, DelegationStateEnum toState,
                         String remark, Map<String, Object> mapValue) {;

        // 将 mapValue 转换为 json
        String stringMapValue;
        try {
            stringMapValue = mapValue == null ? null : new ObjectMapper().writeValueAsString(mapValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 创建流程操作记录
        FlowLogDO flowLogDO = new FlowLogDO()
                .setDelegationId(delegationId)
                .setFromState(Optional.ofNullable(fromState).map(DelegationStateEnum::getState).orElse(null))
                .setToState(Optional.ofNullable(toState).map(DelegationStateEnum::getState).orElse(null))
                .setOperatorId(operatorId)
                .setOperateTime(new Date())
                .setRemark(remark)
                .setMapValue(stringMapValue);

        // 插入流程操作记录
        flowLogMapper.insert(flowLogDO);

    }

}
