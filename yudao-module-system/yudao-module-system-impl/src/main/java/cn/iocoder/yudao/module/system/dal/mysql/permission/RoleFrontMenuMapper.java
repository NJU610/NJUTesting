package cn.iocoder.yudao.module.system.dal.mysql.permission;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleFrontMenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色前台菜单 Mapper
 *
 * @author qjy
 */
@Mapper
public interface RoleFrontMenuMapper extends BaseMapperX<RoleFrontMenuDO> {

//    default PageResult<RoleFrontMenuDO> selectPage(RoleFrontMenuPageReqVO reqVO) {
//        return selectPage(reqVO, new LambdaQueryWrapperX<RoleFrontMenuDO>()
//                .orderByDesc(RoleFrontMenuDO::getId));
//    }
//
//    default List<RoleFrontMenuDO> selectList(RoleFrontMenuExportReqVO reqVO) {
//        return selectList(new LambdaQueryWrapperX<RoleFrontMenuDO>()
//                .orderByDesc(RoleFrontMenuDO::getId));
//    }

}
