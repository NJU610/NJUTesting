package cn.iocoder.yudao.module.system.dal.mysql.flow;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;

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

    default List<FlowDO> selectList(FlowQueryVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FlowDO>()
                .betweenIfPresent(FlowDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .eqIfPresent(FlowDO::getId, reqVO.getId())
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

}
