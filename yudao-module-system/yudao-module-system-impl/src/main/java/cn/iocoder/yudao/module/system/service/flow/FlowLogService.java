package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import com.tencentcloudapi.vpc.v20170312.models.FlowLog;

import java.util.List;
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


    List<FlowLogDO> listLogs(Long delegationId);

}
