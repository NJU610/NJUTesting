package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;

/**
 * 流程操作 Service 接口
 *
 * @author qjy
 */
public interface FlowLogService {

    /**
     * 保存操作日志
     * @param flowId 流程编号
     * @param fromState 原状态
     * @param toState 目标状态
     */
    void saveLog (Long flowId, FlowStateEnum fromState, FlowStateEnum toState);

}
