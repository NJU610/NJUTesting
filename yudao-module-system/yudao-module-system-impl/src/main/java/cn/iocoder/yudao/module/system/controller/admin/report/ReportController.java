package cn.iocoder.yudao.module.system.controller.admin.report;

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

import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.system.convert.report.ReportConvert;
import cn.iocoder.yudao.module.system.service.report.ReportService;

@Api(tags = "测试报告")
@RestController
@RequestMapping("/system/report")
@Validated
public class ReportController {

    @Resource
    private ReportService reportService;

    @PostMapping("/create")
    @ApiOperation("测试部人员-创建测试报告")
    public CommonResult<Long> createReport(@Valid @RequestBody ReportCreateReqVO createReqVO) {
        return success(reportService.createReport(createReqVO));
    }

    @PutMapping("/save/table7")
    @ApiOperation("保存软件测试报告")
    public CommonResult<Boolean> saveReportTable7(@Valid @RequestBody ReportSaveTableReqVO saveReqVO) {
        reportService.saveReportTable7(saveReqVO);
        return success(true);
    }

    @PutMapping("/save/table8")
    @ApiOperation("保存测试用例（电子记录）")
    public CommonResult<Boolean> saveReportTable8(@Valid @RequestBody ReportSaveTableReqVO saveReqVO) {
        reportService.saveReportTable8(saveReqVO);
        return success(true);
    }

    @PutMapping("/save/table9")
    @ApiOperation("保存软件测试记录（电子记录）")
    public CommonResult<Boolean> saveReportTable9(@Valid @RequestBody ReportSaveTableReqVO saveReqVO) {
        reportService.saveReportTable9(saveReqVO);
        return success(true);
    }

    @PutMapping("/save/table10")
    @ApiOperation("保存测试报告检查表")
    public CommonResult<Boolean> saveReportTable10(@Valid @RequestBody ReportSaveTableReqVO saveReqVO) {
        reportService.saveReportTable10(saveReqVO);
        return success(true);
    }

    @PutMapping("/save/table11")
    @ApiOperation("保存软件测试问题清单（电子记录）")
    public CommonResult<Boolean> saveReportTable11(@Valid @RequestBody ReportSaveTableReqVO saveReqVO) {
        reportService.saveReportTable11(saveReqVO);
        return success(true);
    }

    @PutMapping("/submit")
    @ApiOperation("测试部人员-提交测试报告")
    public CommonResult<Boolean> submitReport(@Valid @RequestBody ReportSubmitReqVO submitReqVO) {
        reportService.submitReport(submitReqVO);
        return success(true);
    }

    @PutMapping("/accept/manager")
    @ApiOperation("测试部主管-审核测试报告通过")
    public CommonResult<Boolean> acceptReportManager(@Valid @RequestBody ReportAcceptReqVO acceptReqVO) {
        reportService.acceptReportManager(acceptReqVO);
        return success(true);
    }

    @PutMapping("/reject/manager")
    @ApiOperation("测试部主管-审核测试报告不通过")
    public CommonResult<Boolean> rejectReportManager(@Valid @RequestBody ReportRejectReqVO rejectReqVO) {
        reportService.rejectReportManager(rejectReqVO);
        return success(true);
    }

    @PutMapping("/accept/client")
    @ApiOperation("客户-审核测试报告通过")
    public CommonResult<Boolean> acceptReportClient(@Valid @RequestBody ReportAcceptReqVO acceptReqVO) {
        reportService.acceptReportClient(acceptReqVO);
        return success(true);
    }

    @PutMapping("/reject/client")
    @ApiOperation("客户-审核测试报告不通过")
    public CommonResult<Boolean> rejectReportClient(@Valid @RequestBody ReportRejectReqVO rejectReqVO) {
        reportService.rejectReportClient(rejectReqVO);
        return success(true);
    }

    @PutMapping("/accept/signatory")
    @ApiOperation("授权签字人-审核测试报告通过")
    public CommonResult<Boolean> acceptReportSignatory(@Valid @RequestBody ReportAcceptReqVO acceptReqVO) {
        reportService.acceptReportSignatory(acceptReqVO);
        return success(true);
    }

    @PutMapping("/reject/signatory")
    @ApiOperation("授权签字人-审核测试报告不通过")
    public CommonResult<Boolean> rejectReportSignatory(@Valid @RequestBody ReportRejectReqVO rejectReqVO) {
        reportService.rejectReportSignatory(rejectReqVO);
        return success(true);
    }

    @PutMapping("/archive")
    @ApiOperation("测试部-归档测试报告")
    public CommonResult<Boolean> archiveReport(@Valid @RequestBody ReportArchiveReqVO archiveReqVO) {
        reportService.archiveReport(archiveReqVO);
        return success(true);
    }

    @PutMapping("/send")
    @ApiOperation("市场部-发送测试报告")
    public CommonResult<Boolean> sendReport(@Valid @RequestBody ReportSendReqVO sendReqVO) {
        reportService.sendReport(sendReqVO);
        return success(true);
    }

    @PutMapping("/receive")
    @ApiOperation("客户-确认接收测试报告")
    public CommonResult<Boolean> receiveReport(@Valid @RequestBody ReportReceiveReqVO receiveReqVO) {
        reportService.receiveReport(receiveReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得测试报告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<ReportRespVO> getReport(@RequestParam("id") Long id) {
        ReportDO report = reportService.getReport(id);
        return success(ReportConvert.INSTANCE.convert(report));
    }

    @GetMapping("/get/table7")
    @ApiOperation("获得软件测试报告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<String> getReportTable7(@RequestParam("id") String id) {
        return success(reportService.getReportTable("table7", id));
    }

    @GetMapping("/get/table8")
    @ApiOperation("获得测试用例（电子记录）")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<String> getReportTable8(@RequestParam("id") String id) {
        return success(reportService.getReportTable("table8", id));
    }

    @GetMapping("/get/table9")
    @ApiOperation("获得软件测试记录（电子记录）")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<String> getReportTable9(@RequestParam("id") String id) {
        return success(reportService.getReportTable("table9", id));
    }

    @GetMapping("/get/table10")
    @ApiOperation("获得测试报告检查表")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<String> getReportTable10(@RequestParam("id") String id) {
        return success(reportService.getReportTable("table10", id));
    }

    @GetMapping("/get/table11")
    @ApiOperation("获得软件测试问题清单（电子记录）")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<String> getReportTable11(@RequestParam("id") String id) {
        return success(reportService.getReportTable("table11", id));
    }

    @GetMapping("/list")
    @ApiOperation("获得测试报告列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<ReportRespVO>> getReportList(@RequestParam("ids") Collection<Long> ids) {
        List<ReportDO> list = reportService.getReportList(ids);
        return success(ReportConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得测试报告分页")
    public CommonResult<PageResult<ReportRespVO>> getReportPage(@Valid ReportPageReqVO pageVO) {
        PageResult<ReportDO> pageResult = reportService.getReportPage(pageVO);
        return success(ReportConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出测试报告 Excel")
    @OperateLog(type = EXPORT)
    public void exportReportExcel(@Valid ReportExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<ReportDO> list = reportService.getReportList(exportReqVO);
        // 导出 Excel
        List<ReportExcelVO> datas = ReportConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "测试报告.xls", "数据", ReportExcelVO.class, datas);
    }

}
