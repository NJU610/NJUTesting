package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;

import java.util.Map;

/**
 * 流程操作 Service 接口
 *
 * @author qjy
 */
public interface FlowLogService {

    /**
     * 保存操作日志
     * @param delegationId 委托 id
     * @param operatorId 操作者 id
     * @param fromState 原始状态
     * @param toState 目标状态
     * @param templateParams 操作变量
     */
    void saveLog (Long delegationId, Long operatorId, DelegationStateEnum fromState, DelegationStateEnum toState, String remark, Map<String, Object> templateParams);

}
