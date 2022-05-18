package cn.iocoder.yudao.module.system.controller.admin.permission;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.permission.FrontPermissionAssignRoleMenuReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.permission.FrontPermissionAssignUserRoleReqVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.FrontUserSimpleRespVO;
import cn.iocoder.yudao.module.system.service.permission.FrontPermissionService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 *
 * @author qjy
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/system/front/permission")
public class FrontPermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;
    @Resource
    private RoleService roleService;
    @Resource
    private FrontPermissionService frontPermissionService;


    @ApiOperation("获得角色拥有的前台菜单编号")
    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-role-resources")
    public CommonResult<Set<Long>> listRoleMenus(Long roleId) {
        return success(frontPermissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/assign-role-menu")
    @ApiOperation("赋予角色菜单")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody FrontPermissionAssignRoleMenuReqVO reqVO) {
        // 执行菜单的分配
        frontPermissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @ApiOperation("获得用户拥有的角色编号列表")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-user-roles")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(frontPermissionService.getUserRoleIdListByUserId(userId));
    }

    @ApiOperation("赋予用户角色")
    @PostMapping("/assign-user-role")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody FrontPermissionAssignUserRoleReqVO reqVO) {
        frontPermissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

    @ApiOperation("获得拥有某个角色的用户编号集合")
    @ApiImplicitParam(name = "roleCode", value = "角色Code", required = true, dataTypeClass = String.class)
    @PostMapping("/list-role-users")
    public CommonResult<Set<Long>> listUserIdsByRoleCode(@RequestParam("roleCode")String roleCode) {
        return success(frontPermissionService.getUserRoleIdListByRoleId(roleService.getRoleByCode(roleCode).getId()));
    }

    @ApiOperation("获得拥有某个角色的用户精简信息集合")
    @ApiImplicitParam(name = "roleCode", value = "角色Code", required = true, dataTypeClass = String.class)
    @PostMapping("/list-role-simple-users")
    public CommonResult<Set<FrontUserSimpleRespVO>> listSimpleUsersByRoleCode(@RequestParam("roleCode")String roleCode) {
        return success(frontPermissionService.getSimpleUserListByRoleId(roleService.getRoleByCode(roleCode).getId()));
    }

}
