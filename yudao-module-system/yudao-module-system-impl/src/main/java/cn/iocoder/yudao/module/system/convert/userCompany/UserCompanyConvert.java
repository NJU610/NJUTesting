package cn.iocoder.yudao.module.system.convert.userCompany;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyRespVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;

/**
 * 用户公司关联 Convert
 *
 * @author qjy
 */
@Mapper
public interface UserCompanyConvert {

    UserCompanyConvert INSTANCE = Mappers.getMapper(UserCompanyConvert.class);

    UserCompanyDO convert(UserCompanyCreateReqVO bean);

    UserCompanyDO convert(UserCompanyUpdateReqVO bean);

    UserCompanyRespVO convert(UserCompanyDO bean);

    List<UserCompanyRespVO> convertList(List<UserCompanyDO> list);

    PageResult<UserCompanyRespVO> convertPage(PageResult<UserCompanyDO> page);

    List<UserCompanyExcelVO> convertList02(List<UserCompanyDO> list);

}
