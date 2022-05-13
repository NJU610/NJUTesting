package cn.iocoder.yudao.module.system.dal.mysql.flow;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 流程操作 Mapper
 *
 * @author qjy
 */
@Mapper
public interface FlowLogMapper extends BaseMapperX<FlowLogDO> {

    default PageResult<FlowLogDO> selectPage(FlowLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FlowLogDO>()
                .eqIfPresent(FlowLogDO::getDelegationId, reqVO.getDelegationId())
                .eqIfPresent(FlowLogDO::getFromState, reqVO.getFromState())
                .eqIfPresent(FlowLogDO::getToState, reqVO.getToState())
                .eqIfPresent(FlowLogDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(FlowLogDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FlowLogDO::getOperateTime, reqVO.getBeginOperateTime(), reqVO.getEndOperateTime())
                .betweenIfPresent(FlowLogDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(FlowLogDO::getMapValue, reqVO.getMapValue())
                .orderByDesc(FlowLogDO::getId));
    }

    default List<FlowLogDO> selectList(FlowLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FlowLogDO>()
                .eqIfPresent(FlowLogDO::getDelegationId, reqVO.getDelegationId())
                .eqIfPresent(FlowLogDO::getFromState, reqVO.getFromState())
                .eqIfPresent(FlowLogDO::getToState, reqVO.getToState())
                .eqIfPresent(FlowLogDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(FlowLogDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(FlowLogDO::getOperateTime, reqVO.getBeginOperateTime(), reqVO.getEndOperateTime())
                .betweenIfPresent(FlowLogDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(FlowLogDO::getMapValue, reqVO.getMapValue())
                .orderByDesc(FlowLogDO::getId));
    }

}
