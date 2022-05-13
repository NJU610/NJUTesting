package cn.iocoder.yudao.module.system.convert.report;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;

/**
 * 测试报告 Convert
 *
 * @author lyw
 */
@Mapper
public interface ReportConvert {

    ReportConvert INSTANCE = Mappers.getMapper(ReportConvert.class);

    ReportDO convert(ReportCreateReqVO bean);

    ReportRespVO convert(ReportDO bean);

    List<ReportRespVO> convertList(List<ReportDO> list);

    PageResult<ReportRespVO> convertPage(PageResult<ReportDO> page);

    List<ReportExcelVO> convertList02(List<ReportDO> list);

}
