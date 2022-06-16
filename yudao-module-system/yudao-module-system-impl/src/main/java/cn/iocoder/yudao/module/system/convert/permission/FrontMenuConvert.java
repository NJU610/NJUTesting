package cn.iocoder.yudao.module.system.convert.permission;


import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.*;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前台菜单 Convert
 *
 * @author qjy
 */
@Mapper
public interface FrontMenuConvert {

    FrontMenuConvert INSTANCE = Mappers.getMapper(FrontMenuConvert.class);

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    default FrontMenuDO convert(FrontMenuCreateReqVO bean) {
        FrontMenuDO menu = new FrontMenuDO();
        BeanUtil.copyProperties(bean, menu, "parentKeys");
        try {
            menu.setParentKeys(OBJECT_MAPPER.writeValueAsString(bean.getParentKeys()));
        } catch (Exception e) {
        }
        return menu;
    }

    default FrontMenuDO convert(FrontMenuUpdateReqVO bean) {
        FrontMenuDO menu = new FrontMenuDO();
        BeanUtil.copyProperties(bean, menu, "parentKeys");
        try {
            menu.setParentKeys(OBJECT_MAPPER.writeValueAsString(bean.getParentKeys()));
        } catch (Exception e) {
        }
        return menu;
    }

    default FrontMenuRespVO convert(FrontMenuDO bean) {
        FrontMenuRespVO menu = new FrontMenuRespVO();
        BeanUtil.copyProperties(bean, menu, "parentKeys");
        try {
            menu.setParentKeys(OBJECT_MAPPER.readValue(bean.getParentKeys(), List.class));
        } catch (Exception e) {
        }
        return menu;
    }

    default FrontMenuSimpleRespVO convert02(FrontMenuDO bean) {
        FrontMenuSimpleRespVO menu = new FrontMenuSimpleRespVO();
        BeanUtil.copyProperties(bean, menu, "parentKeys");
        try {
            menu.setParentKeys(OBJECT_MAPPER.readValue(bean.getParentKeys(), List.class));
        } catch (Exception e) {
        }
        return menu;
    }

    default List<FrontMenuRespVO> convertList(List<FrontMenuDO> list) {
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    PageResult<FrontMenuRespVO> convertPage(PageResult<FrontMenuDO> page);

    List<FrontMenuExcelVO> convertList02(List<FrontMenuDO> list);

    default List<FrontMenuSimpleRespVO> convertList03(List<FrontMenuDO> list) {
        return list.stream().map(this::convert02).collect(Collectors.toList());
    }

}
