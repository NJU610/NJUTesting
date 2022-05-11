package cn.iocoder.yudao.module.system.controller.admin.sample;

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

import cn.iocoder.yudao.module.system.controller.admin.sample.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;
import cn.iocoder.yudao.module.system.convert.sample.SampleConvert;
import cn.iocoder.yudao.module.system.service.sample.SampleService;

@Api(tags = "样品")
@RestController
@RequestMapping("/system/sample")
@Validated
public class SampleController {

    @Resource
    private SampleService sampleService;

    @PostMapping("/create")
    @ApiOperation("创建样品")
    public CommonResult<Long> createSample(@Valid @RequestBody SampleCreateReqVO createReqVO) {
        return success(sampleService.createSample(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新样品")
    public CommonResult<Boolean> updateSample(@Valid @RequestBody SampleUpdateReqVO updateReqVO) {
        sampleService.updateSample(updateReqVO);
        return success(true);
    }

    @PutMapping("/submit")
    @ApiOperation(value = "客户提交样品", notes = "用于需要修改样品时，客户重新填写信息后提交样品")
    public CommonResult<Boolean> submitSample(@Valid @RequestBody SampleSubmitReqVO submitReqVO) {
        sampleService.submitSample(submitReqVO);
        return success(true);
    }

    @PutMapping("/audit/success")
    @ApiOperation("审核样品通过")
    public CommonResult<Boolean> auditSampleSuccess(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleSuccess(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/type1")
    @ApiOperation("样品验收不通过，用户重新发送样品中")
    public CommonResult<Boolean> auditSampleFailResend(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleFail1(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/type2")
    @ApiOperation("样品验收不通过，用户修改样品信息中")
    public CommonResult<Boolean> auditSampleFailModify(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleFail2(auditReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除样品")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteSample(@RequestParam("id") Long id) {
        sampleService.deleteSample(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得样品")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<SampleRespVO> getSample(@RequestParam("id") Long id) {
        SampleDO sample = sampleService.getSample(id);
        return success(SampleConvert.INSTANCE.convert(sample));
    }

    @GetMapping("/list")
    @ApiOperation("获得样品列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<SampleRespVO>> getSampleList(@RequestParam("ids") Collection<Long> ids) {
        List<SampleDO> list = sampleService.getSampleList(ids);
        return success(SampleConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得样品分页")
    public CommonResult<PageResult<SampleRespVO>> getSamplePage(@Valid SamplePageReqVO pageVO) {
        PageResult<SampleDO> pageResult = sampleService.getSamplePage(pageVO);
        return success(SampleConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出样品 Excel")
    @OperateLog(type = EXPORT)
    public void exportSampleExcel(@Valid SampleExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<SampleDO> list = sampleService.getSampleList(exportReqVO);
        // 导出 Excel
        List<SampleExcelVO> datas = SampleConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "样品.xls", "数据", SampleExcelVO.class, datas);
    }

}
