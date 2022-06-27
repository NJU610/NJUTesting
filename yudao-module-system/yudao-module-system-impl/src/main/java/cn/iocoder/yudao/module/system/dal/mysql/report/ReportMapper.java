package cn.iocoder.yudao.module.system.dal.mysql.report;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;

/**
 * 测试报告 Mapper
 *
 * @author lyw
 */
@Mapper
public interface ReportMapper extends BaseMapperX<ReportDO> {

    default boolean existsByTable7Id(Long table7Id) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .eq(ReportDO::getTable7Id,
                        table7Id)) > 0;
    }

    default boolean existsByTable8Id(Long table8Id) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .eq(ReportDO::getTable7Id,
                        table8Id)) > 0;
    }

    default boolean existsByTable9Id(Long table9Id) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .eq(ReportDO::getTable9Id,
                        table9Id)) > 0;
    }

    default boolean existsByTable10Id(Long table10Id) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .eq(ReportDO::getTable10Id,
                        table10Id)) > 0;
    }

    default boolean existsByTable11Id(Long table11Id) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .eq(ReportDO::getTable11Id,
                        table11Id)) > 0;
    }

    default boolean existsByTime(Date beginTime, Date endTime) {
        return selectCount(new LambdaQueryWrapperX<ReportDO>()
                .between(ReportDO::getCreateTime,
                        beginTime,
                        endTime)) > 0;
    }

    default PageResult<ReportDO> selectPage(ReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDO>()
                .eqIfPresent(ReportDO::getTable7Id, reqVO.getTable7Id())
                .eqIfPresent(ReportDO::getTable8Id, reqVO.getTable8Id())
                .eqIfPresent(ReportDO::getTable9Id, reqVO.getTable9Id())
                .eqIfPresent(ReportDO::getTable11Id, reqVO.getTable11Id())
                .eqIfPresent(ReportDO::getTestingDeptManagerId, reqVO.getTestingDeptManagerId())
                .eqIfPresent(ReportDO::getTable10Id, reqVO.getTable10Id())
                .eqIfPresent(ReportDO::getSignatoryId, reqVO.getSignatoryId())
                .betweenIfPresent(ReportDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ReportDO::getId));
    }

    default List<ReportDO> selectList(ReportExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ReportDO>()
                .eqIfPresent(ReportDO::getTable7Id, reqVO.getTable7Id())
                .eqIfPresent(ReportDO::getTable8Id, reqVO.getTable8Id())
                .eqIfPresent(ReportDO::getTable9Id, reqVO.getTable9Id())
                .eqIfPresent(ReportDO::getTable11Id, reqVO.getTable11Id())
                .eqIfPresent(ReportDO::getTestingDeptManagerId, reqVO.getTestingDeptManagerId())
                .eqIfPresent(ReportDO::getTable10Id, reqVO.getTable10Id())
                .eqIfPresent(ReportDO::getSignatoryId, reqVO.getSignatoryId())
                .betweenIfPresent(ReportDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ReportDO::getId));
    }

}
