package cn.iocoder.yudao.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.system.controller.admin.permission.FrontRoleController;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.FrontUserSimpleRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleFrontMenuDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuBatchInsertMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleBatchInsertMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限 Service 实现类
 *
 * @author 秦嘉余
 */
@Service
public class FrontPermissionServiceImpl implements FrontPermissionService {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleFrontMenuMapper roleFrontMenuMapper;

    @Resource
    private RoleFrontMenuBatchInsertMapper roleFrontMenuBatchInsertMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserRoleBatchInsertMapper userRoleBatchInsertMapper;

    @Resource
    private AdminUserService userService;

    @Override
    public Set<Long> getRoleMenuIds(Long roleId) {
        if (FrontRoleController.ROLE_IDS.contains(roleId)) {
            return new HashSet<>();
        }

        return CollectionUtils.convertSet(roleFrontMenuMapper.selectListByRoleId(roleId),
                RoleFrontMenuDO::getFrontMenuId);
    }

    @Override
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        Set<Long> dbMenuIds = CollectionUtils.convertSet(roleFrontMenuMapper.selectListByRoleId(roleId),
                RoleFrontMenuDO::getFrontMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            roleFrontMenuBatchInsertMapper.saveBatch(CollectionUtils.convertList(createMenuIds, menuId -> {
                RoleFrontMenuDO entity = new RoleFrontMenuDO();
                entity.setRoleId(roleId);
                entity.setFrontMenuId(menuId);
                return entity;
            }));
        }

        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            roleFrontMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId)
                .stream()
                .filter(roleId -> !FrontRoleController.ROLE_IDS.contains(roleId)).collect(Collectors.toSet());
    }

    @Override
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        roleIds = roleIds.stream().filter(roleId -> !FrontRoleController.ROLE_IDS.contains(roleId)).collect(Collectors.toSet());
        Set<Long> dbRoleIds = CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId),
                UserRoleDO::getRoleId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleBatchInsertMapper.saveBatch(CollectionUtils.convertList(createRoleIds, roleId -> {
                UserRoleDO entity = new UserRoleDO();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            userRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleId(Long roleId) {
        if (FrontRoleController.ROLE_IDS.contains(roleId)) {
            return new HashSet<>();
        }
        return CollectionUtils.convertSet(userRoleMapper.selectListByRoleId(roleId),
                UserRoleDO::getUserId);
    }

    @Override
    public Set<FrontUserSimpleRespVO> getSimpleUserListByRoleId(Long roleId) {
        if (FrontRoleController.ROLE_IDS.contains(roleId)) {
            return new HashSet<>();
        }
        return CollectionUtils.convertSet(userRoleMapper.selectListByRoleId(roleId),
                userRoleDO -> new FrontUserSimpleRespVO()
                        .setId(userRoleDO.getUserId())
                        .setNickname(userService.getUser(userRoleDO.getUserId()).getNickname()));
    }
}
