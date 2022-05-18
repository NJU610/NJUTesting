package cn.iocoder.yudao.module.system.convert.permission;


import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuRespVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 前台菜单 Convert
 *
 * @author qjy
 */
@Mapper
public interface FrontMenuConvert {

    FrontMenuConvert INSTANCE = Mappers.getMapper(FrontMenuConvert.class);

    FrontMenuDO convert(FrontMenuCreateReqVO bean);

    FrontMenuDO convert(FrontMenuUpdateReqVO bean);

    FrontMenuRespVO convert(FrontMenuDO bean);

    List<FrontMenuRespVO> convertList(List<FrontMenuDO> list);

    PageResult<FrontMenuRespVO> convertPage(PageResult<FrontMenuDO> page);

    List<FrontMenuExcelVO> convertList02(List<FrontMenuDO> list);

}
