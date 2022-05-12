package cn.iocoder.yudao.module.system.controller.admin.sample;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
    @ApiOperation(value = "客户-创建样品",
            notes = "客户首次创建样品时调用，之后修改样品信息时调用update和submit接口。返回值为样品编号。")
    public CommonResult<Long> createSample(@Valid @RequestBody SampleCreateReqVO createReqVO) {
        return success(sampleService.createSample(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "客户-更新样品",
            notes = "客户修改样品信息。需要填写id和要更新的字段。id为样品编号，通过查询委托信息可以获取。返回值为是否更新成功。")
    public CommonResult<Boolean> updateSample(@Valid @RequestBody SampleUpdateReqVO updateReqVO) {
        sampleService.updateSample(updateReqVO);
        return success(true);
    }

    @PutMapping("/submit")
    @ApiOperation(value = "客户-提交样品",
            notes = "用于需要修改样品时，客户重新填写信息后提交样品。" +
                    "需要传入id字段。id为样品编号，创建样品时不需要调用此接口。返回值为是否提交成功")
    public CommonResult<Boolean> submitSample(@Valid @RequestBody SampleSubmitReqVO submitReqVO) {
        sampleService.submitSample(submitReqVO);
        return success(true);
    }

    @PutMapping("/audit/success")
    @ApiOperation(value = "市场部/测试部负责人-样品验收通过",
            notes = "需要传入id和remark字段。id为样品编号，remark为审核意见。返回值为是否更新成功。")
    public CommonResult<Boolean> auditSampleSuccess(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleSuccess(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/resend")
    @ApiOperation(value = "市场部/测试部负责人-样品验收不通过，用户重新发送样品中",
            notes = "需要传入id和remark字段。id为样品编号，remark为审核意见。返回值为是否更新成功。")
    public CommonResult<Boolean> auditSampleFailResend(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleFailResend(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/modify")
    @ApiOperation(value = "市场部/测试部负责人-样品验收不通过，用户修改样品信息中",
            notes = "需要传入id和remark字段。id为样品编号，remark为审核意见。返回值为是否更新成功。")
    public CommonResult<Boolean> auditSampleFailModify(@Valid @RequestBody SampleAuditReqVO auditReqVO) {
        sampleService.auditSampleFailModify(auditReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除样品", notes = "需要传入id字段。id为样品编号。返回值为是否删除成功。")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteSample(@RequestParam("id") Long id) {
        sampleService.deleteSample(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获得样品", notes = "需要传入id字段。id为样品编号。返回值为样品信息。")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<SampleRespVO> getSample(@RequestParam("id") Long id) {
        SampleDO sample = sampleService.getSample(id);
        return success(SampleConvert.INSTANCE.convert(sample));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得样品列表", notes = "需要传入多个id。id为样品编号。返回值为样品列表。")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<SampleRespVO>> getSampleList(@RequestParam("ids") Collection<Long> ids) {
        List<SampleDO> list = sampleService.getSampleList(ids);
        return success(SampleConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得样品分页", notes = "需要传入分页参数，可以通过传入查询条件进行条件查询。返回值为样品分页。")
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
