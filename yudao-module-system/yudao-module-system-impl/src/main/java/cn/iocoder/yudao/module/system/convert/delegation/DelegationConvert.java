package cn.iocoder.yudao.module.system.convert.delegation;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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

    @Mapping(source = "state", target = "state", qualifiedByName = "stateCode2Desc")
    DelegationRespVO convert(DelegationDO bean);

    List<DelegationRespVO> convertList(List<DelegationDO> list);

    PageResult<DelegationRespVO> convertPage(PageResult<DelegationDO> page);

    List<DelegationExcelVO> convertList02(List<DelegationDO> list);

    @Named("stateCode2Desc")
    static String stateCode2Desc(Integer state) {
        DelegationStateEnum stateEnum = DelegationStateEnum.getByState(state);
        return stateEnum == null ? null : stateEnum.getDesc();
    }

}
