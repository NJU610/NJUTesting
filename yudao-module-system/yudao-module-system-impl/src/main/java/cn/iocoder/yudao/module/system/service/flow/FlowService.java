package cn.iocoder.yudao.module.system.service.flow;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目流程 Service 接口
 *
 * @author lyw
 */
public interface FlowService {

    /**
     * 创建项目流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFlow(@Valid FlowCreateVO createReqVO);

    /**
     * 更新项目流程
     *
     * @param updateReqVO 更新信息
     */
    void updateFlow(@Valid FlowUpdateReqVO updateReqVO);

    /**
     * 根据委托删除项目流程
     *
     * @param delegationId 委托编号
     */
    void deleteFlowByCreator(Long delegationId);

    /**
     * 根据条件获得项目流程
     *
     * @param queryVO 查询条件
     * @return 项目流程列表
     */
    List<FlowDO> getFlowsByCondition(FlowQueryVO queryVO);

    /**
     * 获得全部项目流程
     *
     * @return 项目流程列表
     */
    List<FlowDO> getAllFlows();

    /**
     * 获得项目流程列表
     *
     * @param ids 编号
     * @return 项目流程列表
     */
    List<FlowDO> getFlowList(Collection<Long> ids);

    /**
     * 获得项目流程分页
     *
     * @param pageReqVO 分页查询
     * @return 项目流程分页
     */
    PageResult<FlowDO> getFlowPage(FlowPageReqVO pageReqVO);

    /**
     * 获得项目流程列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目流程列表
     */
    List<FlowDO> getFlowList(FlowExportReqVO exportReqVO);

}
