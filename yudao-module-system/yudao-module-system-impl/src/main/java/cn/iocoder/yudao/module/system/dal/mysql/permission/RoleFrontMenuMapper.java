package cn.iocoder.yudao.module.system.dal.mysql.permission;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleFrontMenuDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleFrontMenuMapper, RoleFrontMenuDO> {
    }

    default List<RoleFrontMenuDO> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<RoleFrontMenuDO>().eq("role_id", roleId));
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<RoleFrontMenuDO>().eq("role_id", roleId)
                .in("front_menu_id", menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new QueryWrapper<RoleFrontMenuDO>().eq("front_menu_id", menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<RoleFrontMenuDO>().eq("role_id", roleId));
    }

    @Select("SELECT id FROM system_role_front_menu WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectExistsByUpdateTimeAfter(Date maxUpdateTime);

    default Set<Long> selectMenuIdsByRoleIds(Collection<Long> roleIds) {
        return selectList(new QueryWrapper<RoleFrontMenuDO>().in("role_id", roleIds))
                .stream()
                .map(RoleFrontMenuDO::getFrontMenuId)
                .collect(Collectors.toSet());
    }

}
