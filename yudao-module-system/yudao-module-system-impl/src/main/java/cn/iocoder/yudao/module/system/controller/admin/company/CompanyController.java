package cn.iocoder.yudao.module.system.controller.admin.company;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.*;

import javax.validation.constraints.*;
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

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.convert.company.CompanyConvert;
import cn.iocoder.yudao.module.system.service.company.CompanyService;

@Api(tags = "管理后台 - 公司")
@RestController
@RequestMapping("/system/company")
@Validated
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/create")
    @ApiOperation("创建公司")
    public CommonResult<Long> createCompany(@Valid @RequestBody CompanyCreateReqVO createReqVO) {
        return success(companyService.createCompany(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新公司")
    public CommonResult<Boolean> updateCompany(@Valid @RequestBody CompanyUpdateReqVO updateReqVO) {
        companyService.updateCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除公司")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteCompany(@RequestParam("id") Long id) {
        companyService.deleteCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得公司")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<CompanyRespVO> getCompany(@RequestParam("id") Long id) {
        CompanyDO company = companyService.getCompany(id);
        return success(CompanyConvert.INSTANCE.convert(company));
    }

    @GetMapping("/list")
    @ApiOperation("获得公司列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<CompanyRespVO>> getCompanyList(@RequestParam("ids") Collection<Long> ids) {
        List<CompanyDO> list = companyService.getCompanyList(ids);
        return success(CompanyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得公司分页")
    public CommonResult<PageResult<CompanyRespVO>> getCompanyPage(@Valid CompanyPageReqVO pageVO) {
        PageResult<CompanyDO> pageResult = companyService.getCompanyPage(pageVO);
        return success(CompanyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出公司 Excel")
    @OperateLog(type = EXPORT)
    public void exportCompanyExcel(@Valid CompanyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CompanyDO> list = companyService.getCompanyList(exportReqVO);
        // 导出 Excel
        List<CompanyExcelVO> datas = CompanyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "公司.xls", "数据", CompanyExcelVO.class, datas);
    }

}
