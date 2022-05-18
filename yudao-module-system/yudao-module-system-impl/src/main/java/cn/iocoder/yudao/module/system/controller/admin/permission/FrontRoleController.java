package cn.iocoder.yudao.module.system.controller.admin.permission;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.role.*;
import cn.iocoder.yudao.module.system.convert.permission.RoleConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.ROLE_NOT_EXISTS;

@Api(tags = "角色")
@RestController
@RequestMapping("/system/front/role")
@Validated
public class FrontRoleController {

    @Resource
    private RoleService roleService;

    public static final List<Long> ROLE_IDS = Arrays.asList(1L, 2L, 101L, 109L, 110L, 111L);

    @PostMapping("/create")
    @ApiOperation("创建角色")
    public CommonResult<Long> createRole(@Valid @RequestBody FrontRoleCreateReqVO reqVO) {
        return success(roleService.createRole(RoleConvert.INSTANCE.convert(reqVO), null));
    }

    @PutMapping("/update")
    @ApiOperation("修改角色")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody FrontRoleUpdateReqVO reqVO) {
        if (ROLE_IDS.contains(reqVO.getId())) {
            throw exception(ROLE_NOT_EXISTS);
        }
        roleService.updateRole(RoleConvert.INSTANCE.convert(reqVO));
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改角色状态")
    public CommonResult<Boolean> updateRoleStatus(@Valid @RequestBody FrontRoleUpdateStatusReqVO reqVO) {
        if (ROLE_IDS.contains(reqVO.getId())) {
            throw exception(ROLE_NOT_EXISTS);
        }
        roleService.updateRoleStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteRole(@RequestParam("id") Long id) {
        if (ROLE_IDS.contains(id)) {
            throw exception(ROLE_NOT_EXISTS);
        }
        roleService.deleteRole(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得角色信息")
    public CommonResult<FrontRoleRespVO> getRole(@RequestParam("id") Long id) {
        RoleDO role = roleService.getRole(id);
        if (ROLE_IDS.contains(id) || role == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        return success(RoleConvert.INSTANCE.convert2(role));
    }

    @GetMapping("/page")
    @ApiOperation("获得角色分页")
    public CommonResult<PageResult<FrontRolePageRespVO>> getRolePage(FrontRolePageReqVO reqVO) {
        PageResult<RoleDO> roles = roleService.getFrontRolePage(reqVO);
        return success(
                new PageResult<>(RoleConvert.INSTANCE.convertList(roles.getList()), roles.getTotal())
        );
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
    public CommonResult<List<FrontRoleSimpleRespVO>> getSimpleRoles() {
        // 获得角色列表，只要开启状态的
        List<RoleDO> list = roleService.getRoles(Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        list = list.stream().filter(role -> !ROLE_IDS.contains(role.getId())).collect(Collectors.toList());
        // 排序后，返回给前端
        list.sort(Comparator.comparing(RoleDO::getSort));
        return success(RoleConvert.INSTANCE.convertList04(list));
    }

//    @GetMapping("/export")
//    @OperateLog(type = EXPORT)
//    public void export(HttpServletResponse response, @Validated RoleExportReqVO reqVO) throws IOException {
//        List<RoleDO> list = roleService.getRoleList(reqVO);
//        List<RoleExcelVO> data = RoleConvert.INSTANCE.convertList03(list);
//        // 输出
//        ExcelUtils.write(response, "角色数据.xls", "角色列表", RoleExcelVO.class, data);
//    }

}
