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

@Api(tags = "公司")
@RestController
@RequestMapping("/system/company")
@Validated
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/create")
    @ApiOperation(value = "创建公司", notes = "管理员使用，用于创建公司")
    public CommonResult<Long> createCompany(@Valid @RequestBody CompanyCreateReqVO createReqVO) {
        return success(companyService.createCompany(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新公司", notes = "管理员使用，用于更新公司，除id字段外，其余字段传入者会更新，未传入者不会更新")
    public CommonResult<Boolean> updateCompany(@Valid @RequestBody CompanyUpdateReqVO updateReqVO) {
        companyService.updateCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除公司", notes = "管理员使用，用于删除公司")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteCompany(@RequestParam("id") Long id) {
        companyService.deleteCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获得公司", notes = "管理员使用，用于获得公司信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<CompanyRespVO> getCompany(@RequestParam("id") Long id) {
        CompanyDO company = companyService.getCompany(id);
        return success(CompanyConvert.INSTANCE.convert(company));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得公司列表", notes = "管理员使用，用于获得传入编号列表的公司信息")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<CompanyRespVO>> getCompanyList(@RequestParam("ids") Collection<Long> ids) {
        List<CompanyDO> list = companyService.getCompanyList(ids);
        return success(CompanyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得公司分页", notes = "管理员使用，用于获得公司信息分页, 可选参数作为查询条件")
    public CommonResult<PageResult<CompanyRespVO>> getCompanyPage(@Valid CompanyPageReqVO pageVO) {
        PageResult<CompanyDO> pageResult = companyService.getCompanyPage(pageVO);
        return success(CompanyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation(value = "导出公司 Excel", notes = "管理员使用，用于导出公司 Excel")
    @OperateLog(type = EXPORT)
    public void exportCompanyExcel(@Valid CompanyExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CompanyDO> list = companyService.getCompanyList(exportReqVO);
        // 导出 Excel
        List<CompanyExcelVO> datas = CompanyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "公司.xls", "数据", CompanyExcelVO.class, datas);
    }

}
