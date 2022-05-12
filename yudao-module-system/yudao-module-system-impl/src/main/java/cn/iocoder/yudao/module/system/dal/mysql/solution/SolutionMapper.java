package cn.iocoder.yudao.module.system.dal.mysql.solution;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.*;

/**
 * 测试方案 Mapper
 *
 * @author lyw
 */
@Mapper
public interface SolutionMapper extends BaseMapperX<SolutionDO> {

    default PageResult<SolutionDO> selectPage(SolutionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SolutionDO>()
                .eqIfPresent(SolutionDO::getTable6Id, reqVO.getTable6Id())
                .eqIfPresent(SolutionDO::getAuditorId, reqVO.getAuditorId())
                .eqIfPresent(SolutionDO::getTable13Id, reqVO.getTable13Id())
                .betweenIfPresent(SolutionDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SolutionDO::getId));
    }

    default List<SolutionDO> selectList(SolutionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SolutionDO>()
                .eqIfPresent(SolutionDO::getTable6Id, reqVO.getTable6Id())
                .eqIfPresent(SolutionDO::getAuditorId, reqVO.getAuditorId())
                .eqIfPresent(SolutionDO::getTable13Id, reqVO.getTable13Id())
                .betweenIfPresent(SolutionDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(SolutionDO::getId));
    }

}
