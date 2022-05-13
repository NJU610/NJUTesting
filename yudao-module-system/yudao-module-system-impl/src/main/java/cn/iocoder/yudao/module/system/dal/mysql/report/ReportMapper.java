package cn.iocoder.yudao.module.system.dal.mysql.report;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
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
