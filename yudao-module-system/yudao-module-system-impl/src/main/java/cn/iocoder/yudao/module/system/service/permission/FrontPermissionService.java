package cn.iocoder.yudao.module.system.service.permission;

import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.FrontUserSimpleRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 权限 Service 接口
 *
 * 提供用户-角色、角色-菜单的权限控制
 *
 * @author qjy
 */
public interface FrontPermissionService {

    /**
     * 获得角色拥有的前台菜单编号集合
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    Set<Long> getRoleMenuIds(Long roleId);

    /**
     * 设置角色前台菜单
     *
     * @param roleId 角色编号
     * @param menuIds 前台菜单编号集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdListByUserId(Long userId);

    /**
     * 设置用户角色
     *
     * @param userId 角色编号
     * @param roleIds 角色编号集合
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

    /**
     * 获得拥有某个角色的用户编号集合
     *
     * @param roleId 角色编号
     * @return 用户编号集合
     */
    Set<Long> getUserRoleIdListByRoleId(Long roleId);

    /**
     * 获得拥有某个角色的用户精简信息集合
     *
     * @param roleId 角色编号
     * @return 用户精简信息集合
     */
    Set<FrontUserSimpleRespVO> getSimpleUserListByRoleId(Long roleId);

    /**
     * 获得某些角色拥有的菜单编号集合
     * @param roleIds 角色编号数组
     * @return 前台菜单集合
     */
    List<FrontMenuDO> getRoleMenuList(Collection<Long> roleIds);
}
