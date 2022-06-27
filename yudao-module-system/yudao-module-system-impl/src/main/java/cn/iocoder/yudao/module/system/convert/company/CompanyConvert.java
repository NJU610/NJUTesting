package cn.iocoder.yudao.module.system.convert.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;

/**
 * 公司 Convert
 *
 * @author qjy
 */
@Mapper
public interface CompanyConvert {

    CompanyConvert INSTANCE = Mappers.getMapper(CompanyConvert.class);

    CompanyDO convert(CompanyCreateReqVO bean);

    CompanyDO convert(CompanyUpdateReqVO bean);

    CompanyRespVO convert(CompanyDO bean);

    List<CompanyRespVO> convertList(List<CompanyDO> list);

    List<CompanySimpleRespVO> convertList03(List<CompanyDO> list);

    PageResult<CompanyRespVO> convertPage(PageResult<CompanyDO> page);

    List<CompanyExcelVO> convertList02(List<CompanyDO> list);

}
