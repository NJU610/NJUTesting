package cn.iocoder.yudao.module.system.dal.mysql.sample;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.*;

/**
 * 样品 Mapper
 *
 * @author lyw
 */
@Mapper
public interface SampleMapper extends BaseMapperX<SampleDO> {

    default PageResult<SampleDO> selectPage(SamplePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SampleDO>()
                .eqIfPresent(SampleDO::getType, reqVO.getType())
                .eqIfPresent(SampleDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(SampleDO::getUrl, reqVO.getUrl())
                .eqIfPresent(SampleDO::getInformation, reqVO.getInformation())
                .eqIfPresent(SampleDO::getAuditorId, reqVO.getVerifyId())
                .eqIfPresent(SampleDO::getState, reqVO.getState())
                .betweenIfPresent(SampleDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SampleDO::getId));
    }

    default List<SampleDO> selectList(SampleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SampleDO>()
                .eqIfPresent(SampleDO::getType, reqVO.getType())
                .eqIfPresent(SampleDO::getProcessType, reqVO.getProcessType())
                .eqIfPresent(SampleDO::getUrl, reqVO.getUrl())
                .eqIfPresent(SampleDO::getInformation, reqVO.getInformation())
                .eqIfPresent(SampleDO::getAuditorId, reqVO.getVerifyId())
                .eqIfPresent(SampleDO::getState, reqVO.getState())
                .betweenIfPresent(SampleDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SampleDO::getId));
    }

}
