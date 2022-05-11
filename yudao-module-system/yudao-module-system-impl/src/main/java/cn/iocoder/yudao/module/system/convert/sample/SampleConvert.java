package cn.iocoder.yudao.module.system.convert.sample;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;

/**
 * 样品 Convert
 *
 * @author lyw
 */
@Mapper
public interface SampleConvert {

    SampleConvert INSTANCE = Mappers.getMapper(SampleConvert.class);

    SampleDO convert(SampleCreateReqVO bean);

    SampleDO convert(SampleUpdateReqVO bean);

    SampleDO convert(SampleAuditReqVO bean);

    SampleRespVO convert(SampleDO bean);

    List<SampleRespVO> convertList(List<SampleDO> list);

    PageResult<SampleRespVO> convertPage(PageResult<SampleDO> page);

    List<SampleExcelVO> convertList02(List<SampleDO> list);

}
