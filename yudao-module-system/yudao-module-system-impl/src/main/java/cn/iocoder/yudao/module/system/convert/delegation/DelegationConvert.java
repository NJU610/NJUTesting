package cn.iocoder.yudao.module.system.convert.delegation;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;

/**
 * 委托 Convert
 *
 * @author lyw
 */
@Mapper
public interface DelegationConvert {

    DelegationConvert INSTANCE = Mappers.getMapper(DelegationConvert.class);

    DelegationDO convert(DelegationCreateReqVO bean);

    DelegationDO convert(DelegationUpdateReqVO bean);

    DelegationRespVO convert(DelegationDO bean);

    List<DelegationRespVO> convertList(List<DelegationDO> list);

    PageResult<DelegationRespVO> convertPage(PageResult<DelegationDO> page);

    List<DelegationExcelVO> convertList02(List<DelegationDO> list);

}
