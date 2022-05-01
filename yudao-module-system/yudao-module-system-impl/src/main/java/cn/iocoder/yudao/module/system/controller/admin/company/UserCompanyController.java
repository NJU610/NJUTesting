package cn.iocoder.yudao.module.system.controller.admin.company;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.*;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.module.system.convert.userCompany.UserCompanyConvert;
import cn.iocoder.yudao.module.system.service.company.UserCompanyService;

@Api(tags = "管理后台 - 用户公司关联")
@RestController
@RequestMapping("/system/user-company")
@Validated
public class UserCompanyController {

    @Resource
    private UserCompanyService userCompanyService;

    @PostMapping("/auth")
    @ApiOperation("用户公司关联授权")
    public CommonResult<Long> createUserCompanyByCode(@Valid @RequestBody UserCompanyCreateByCodeReqVO createReqVO) {
        return success(userCompanyService.createUserCompanyByCode(createReqVO));
    }

    @PostMapping("/create")
    @ApiOperation("创建用户公司关联")
    public CommonResult<Long> createUserCompany(@Valid @RequestBody UserCompanyCreateReqVO createReqVO) {
        return success(userCompanyService.createUserCompany(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新用户公司关联")
    public CommonResult<Boolean> updateUserCompany(@Valid @RequestBody UserCompanyUpdateReqVO updateReqVO) {
        userCompanyService.updateUserCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户公司关联")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteUserCompany(@RequestParam("id") Long id) {
        userCompanyService.deleteUserCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得用户公司关联")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<UserCompanyRespVO> getUserCompany(@RequestParam("id") Long id) {
        UserCompanyDO userCompany = userCompanyService.getUserCompany(id);
        return success(UserCompanyConvert.INSTANCE.convert(userCompany));
    }

    @GetMapping("/list")
    @ApiOperation("获得用户公司关联列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<UserCompanyRespVO>> getUserCompanyList(@RequestParam("ids") Collection<Long> ids) {
        List<UserCompanyDO> list = userCompanyService.getUserCompanyList(ids);
        return success(UserCompanyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得用户公司关联分页")
    public CommonResult<PageResult<UserCompanyRespVO>> getUserCompanyPage(@Valid UserCompanyPageReqVO pageVO) {
        PageResult<UserCompanyDO> pageResult = userCompanyService.getUserCompanyPage(pageVO);
        return success(UserCompanyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出用户公司关联 Excel")
    @OperateLog(type = EXPORT)
    public void exportUserCompanyExcel(@Valid UserCompanyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<UserCompanyDO> list = userCompanyService.getUserCompanyList(exportReqVO);
        // 导出 Excel
        List<UserCompanyExcelVO> datas = UserCompanyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "用户公司关联.xls", "数据", UserCompanyExcelVO.class, datas);
    }

}
