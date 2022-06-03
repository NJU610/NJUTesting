package cn.iocoder.yudao.module.system.service.permission;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuBatchInsertMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleBatchInsertMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


@Import(FrontPermissionServiceImpl.class)
class FrontPermissionServiceImplTest extends BaseDbUnitTest {

    @MockBean
    private RoleService roleService;

    @MockBean
    private FrontMenuService frontMenuService;

    @Resource
    private RoleFrontMenuMapper roleFrontMenuMapper;

    @MockBean
    private RoleFrontMenuBatchInsertMapper roleFrontMenuBatchInsertMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @MockBean
    private UserRoleBatchInsertMapper userRoleBatchInsertMapper;

    @MockBean
    private AdminUserService userService;

    @Resource
    private FrontPermissionService frontPermissionService;


    @Test
    void getRoleMenuIds() {

       assertNotNull(frontPermissionService.getRoleMenuIds(1L));

    }

    @Test
    void assignRoleMenu() {

        frontPermissionService.assignRoleMenu(1L,new HashSet<Long>(){{add(1L);}});

    }

    @Test
    void getUserRoleIdListByUserId() {

        frontPermissionService.getUserRoleIdListByUserId(1L);

    }

    @Test
    void assignUserRole() {

        frontPermissionService.assignUserRole(1L,new HashSet<Long>(){{add(1L);}});

    }

    @Test
    void getUserRoleIdListByRoleId() {

        frontPermissionService.getUserRoleIdListByRoleId(1L);

    }

    @Test
    void getSimpleUserListByRoleId() {

        frontPermissionService.getSimpleUserListByRoleId(1L);

    }

    @Test
    void getRoleMenuList() {

        frontPermissionService.getRoleMenuList(Collections.singleton(1L));

    }
}