package cn.iocoder.yudao.module.system.dal.mysql.delegation;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;

/**
 * 委托 Mapper
 *
 * @author lyw
 */
@Mapper
public interface DelegationMapper extends BaseMapperX<DelegationDO> {

    default PageResult<DelegationDO> selectPage(DelegationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DelegationDO>()
                .eqIfPresent(DelegationDO::getAcceptorId, reqVO.getAcceptorId())
                .betweenIfPresent(DelegationDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .betweenIfPresent(DelegationDO::getAcceptTime, reqVO.getBeginAcceptTime(), reqVO.getEndAcceptTime())
                .betweenIfPresent(DelegationDO::getProcessTime, reqVO.getBeginProcessTime(), reqVO.getEndProcessTime())
                .eqIfPresent(DelegationDO::getTable2Id, reqVO.getTable2Id())
                .eqIfPresent(DelegationDO::getTable3Id, reqVO.getTable3Id())
                .eqIfPresent(DelegationDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(DelegationDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(DelegationDO::getCreatorId, reqVO.getCreatorId())
                .orderByDesc(DelegationDO::getId));
    }

    default List<DelegationDO> selectList(DelegationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DelegationDO>()
                .eqIfPresent(DelegationDO::getAcceptorId, reqVO.getAcceptorId())
                .betweenIfPresent(DelegationDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .betweenIfPresent(DelegationDO::getAcceptTime, reqVO.getBeginAcceptTime(), reqVO.getEndAcceptTime())
                .betweenIfPresent(DelegationDO::getProcessTime, reqVO.getBeginProcessTime(), reqVO.getEndProcessTime())
                .eqIfPresent(DelegationDO::getTable2Id, reqVO.getTable2Id())
                .eqIfPresent(DelegationDO::getTable3Id, reqVO.getTable3Id())
                .eqIfPresent(DelegationDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(DelegationDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eqIfPresent(DelegationDO::getCreatorId, reqVO.getCreatorId())
                .orderByDesc(DelegationDO::getId));
    }

}
