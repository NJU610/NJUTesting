package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import com.tencentcloudapi.vpc.v20170312.models.FlowLog;

import javax.validation.Valid;
import java.util.Collection;
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
    Long saveLog (Long delegationId, Long operatorId, DelegationStateEnum fromState, DelegationStateEnum toState, String remark, Map<String, Object> templateParams);
    //委托id替换成项目id
    Long saveLogByProject (Long ProjectId,
                           Long operatorId,
                           DelegationStateEnum fromState,
                           DelegationStateEnum toState,
                           String remark,
                           Map<String, Object> templateParams);

    /**
     * 获得日志列表
     * @param delegationId
     * @return
     */
    List<FlowLogDO> listLogs(Long delegationId);

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFlowLog(@Valid FlowLogCreateReqVO createReqVO);

    /**
     * 更新
     *
     * @param updateReqVO 更新信息
     */
    void updateFlowLog(@Valid FlowLogUpdateReqVO updateReqVO);

    /**
     * 删除
     *
     * @param id 编号
     */
    void deleteFlowLog(Long id);

    /**
     * 获得
     *
     * @param id 编号
     * @return
     */
    FlowLogDO getFlowLog(Long id);

    /**
     * 获得列表
     *
     * @param ids 编号
     * @return 列表
     */
    List<FlowLogDO> getFlowLogList(Collection<Long> ids);

    /**
     * 获得分页
     *
     * @param pageReqVO 分页查询
     * @return 分页
     */
    PageResult<FlowLogDO> getFlowLogPage(FlowLogPageReqVO pageReqVO);

    /**
     * 获得列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 列表
     */
    List<FlowLogDO> getFlowLogList(FlowLogExportReqVO exportReqVO);

}
