package cn.iocoder.yudao.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.*;
import cn.iocoder.yudao.module.system.convert.user.UserConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.common.SexEnum;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/system/front/user")
@Validated
public class FrontUserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @ApiOperation("新增用户")
    public CommonResult<Long> createUser(@Valid @RequestBody FrontUserCreateReqVO reqVO) {
        Long id = userService.createUser(UserConvert.INSTANCE.convert(reqVO));
        return success(id);
    }

    @PutMapping("update")
    @ApiOperation("修改用户，禁止修改username")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody FrontUserUpdateReqVO reqVO) {
        userService.updateUser(UserConvert.INSTANCE.convert(reqVO));
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("重置用户密码")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody FrontUserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改用户状态")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody FrontUserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得用户分页列表")
    public CommonResult<PageResult<FrontUserPageItemRespVO>> getUserPage(@Valid FrontUserPageReqVO reqVO) {
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(UserConvert.INSTANCE.convert(reqVO));
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // 返回空
        }

        // 获得拼接需要的数据
//        Collection<Long> deptIds = convertList(pageResult.getList(), AdminUserDO::getDeptId);
//        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
        // 拼接结果返回
//        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
//        pageResult.getList().forEach(user -> {
//            UserPageItemRespVO respVO = UserConvert.INSTANCE.convert(user);
//            respVO.setDept(UserConvert.INSTANCE.convert(deptMap.get(user.getDeptId())));
//            userList.add(respVO);
//        });

        return success(new PageResult<>(UserConvert.INSTANCE.convertList05(pageResult.getList()), pageResult.getTotal()));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户，主要用于前端的下拉选项")
    public CommonResult<List<FrontUserSimpleRespVO>> getSimpleUsers() {
        // 获用户门列表，只要开启状态的
        List<AdminUserDO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(UserConvert.INSTANCE.convertList06(list));
    }

    @GetMapping("/get")
    @ApiOperation("获得用户详情")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<FrontUserRespVO> getInfo(@RequestParam("id") Long id) {
        return success(UserConvert.INSTANCE.convert5(userService.getUser(id)));
    }

//    @GetMapping("/export")
//    @ApiOperation("导出用户")
//    @OperateLog(type = EXPORT)
//    public void exportUsers(@Validated UserExportReqVO reqVO,
//                            HttpServletResponse response) throws IOException {
//        // 获得用户列表
//        List<AdminUserDO> users = userService.getUsers(reqVO);
//
//        // 获得拼接需要的数据
//        Collection<Long> deptIds = convertList(users, AdminUserDO::getDeptId);
//        Map<Long, DeptDO> deptMap = deptService.getDeptMap(deptIds);
//        Map<Long, AdminUserDO> deptLeaderUserMap = userService.getUserMap(
//                convertSet(deptMap.values(), DeptDO::getLeaderUserId));
//        // 拼接数据
//        List<UserExcelVO> excelUsers = new ArrayList<>(users.size());
//        users.forEach(user -> {
//            UserExcelVO excelVO = UserConvert.INSTANCE.convert02(user);
//            // 设置部门
//            MapUtils.findAndThen(deptMap, user.getDeptId(), dept -> {
//                excelVO.setDeptName(dept.getName());
//                // 设置部门负责人的名字
//                MapUtils.findAndThen(deptLeaderUserMap, dept.getLeaderUserId(),
//                        deptLeaderUser -> excelVO.setDeptLeaderNickname(deptLeaderUser.getNickname()));
//            });
//            excelUsers.add(excelVO);
//        });
//
//        // 输出
//        ExcelUtils.write(response, "用户数据.xls", "用户列表", UserExcelVO.class, excelUsers);
//    }
//
//    @GetMapping("/get-import-template")
//    @ApiOperation("获得导入用户模板")
//    public void importTemplate(HttpServletResponse response) throws IOException {
//        // 手动创建导出 demo
//        List<UserImportExcelVO> list = Arrays.asList(
//                UserImportExcelVO.builder().username("yunai").deptId(1L).email("yunai@iocoder.cn").mobile("15601691300")
//                        .nickname("芋道").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
//                UserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@iocoder.cn").mobile("15601701300")
//                        .nickname("源码").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
//        );
//
//        // 输出
//        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", UserImportExcelVO.class, list);
//    }
//
//    @PostMapping("/import")
//    @ApiOperation("导入用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", value = "Excel 文件", required = true, dataTypeClass = MultipartFile.class),
//            @ApiImplicitParam(name = "updateSupport", value = "是否支持更新，默认为 false", example = "true", dataTypeClass = Boolean.class)
//    })
//    public CommonResult<UserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
//                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
//        List<UserImportExcelVO> list = ExcelUtils.read(file, UserImportExcelVO.class);
//        return success(userService.importUsers(list, updateSupport));
//    }

}
