package cn.iocoder.yudao.module.system.dal.mysql.flow;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.FLOW_NOT_EXISTS;

/**
 * 项目流程 Mapper
 *
 * @author lyw
 */
@Mapper
public interface FlowMapper extends BaseMapperX<FlowDO> {

    default PageResult<FlowDO> selectPage(FlowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FlowDO>()
                .betweenIfPresent(FlowDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .eqIfPresent(FlowDO::getCreatorId, reqVO.getCreatorId())
                .eqIfPresent(FlowDO::getDelegationId, reqVO.getDelegationId())
                .eqIfPresent(FlowDO::getContractId, reqVO.getContractId())
                .eqIfPresent(FlowDO::getSampleId, reqVO.getSampleId())
                .eqIfPresent(FlowDO::getSolutionId, reqVO.getSolutionId())
                .eqIfPresent(FlowDO::getReportId, reqVO.getReportId())
                .eqIfPresent(FlowDO::getState, reqVO.getState())
                .betweenIfPresent(FlowDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(FlowDO::getId));
    }

    default List<FlowDO> selectList(FlowExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FlowDO>()
                .betweenIfPresent(FlowDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .eqIfPresent(FlowDO::getCreatorId, reqVO.getCreatorId())
                .eqIfPresent(FlowDO::getDelegationId, reqVO.getDelegationId())
                .eqIfPresent(FlowDO::getContractId, reqVO.getContractId())
                .eqIfPresent(FlowDO::getSampleId, reqVO.getSampleId())
                .eqIfPresent(FlowDO::getSolutionId, reqVO.getSolutionId())
                .eqIfPresent(FlowDO::getReportId, reqVO.getReportId())
                .eqIfPresent(FlowDO::getState, reqVO.getState())
                .betweenIfPresent(FlowDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(FlowDO::getId));
    }

    default FlowDO selectByDelegation(Long delegationId) {
        FlowDO flow = selectOne(new LambdaQueryWrapperX<FlowDO>()
                .eqIfPresent(FlowDO::getDelegationId, delegationId)
                .eqIfPresent(FlowDO::getDeleted, false));
        if (flow == null) {
            throw exception(FLOW_NOT_EXISTS);
        }
        return flow;
    }

}
