package cn.iocoder.yudao.module.system.controller.admin.flow;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.convert.flow.FlowLogConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理后台 - ")
@RestController
@RequestMapping("/system/flow-log")
@Validated
public class FlowLogController {

    @Resource
    private FlowLogService flowLogService;

    @PostMapping("/create")
    @ApiOperation("创建")
    @PreAuthorize("@ss.hasPermission('system:flow-log:create')")
    public CommonResult<Long> createFlowLog(@Valid @RequestBody FlowLogCreateReqVO createReqVO) {
        return success(flowLogService.createFlowLog(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新")
    @PreAuthorize("@ss.hasPermission('system:flow-log:update')")
    public CommonResult<Boolean> updateFlowLog(@Valid @RequestBody FlowLogUpdateReqVO updateReqVO) {
        flowLogService.updateFlowLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:flow-log:delete')")
    public CommonResult<Boolean> deleteFlowLog(@RequestParam("id") Long id) {
        flowLogService.deleteFlowLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:flow-log:query')")
    public CommonResult<FlowLogRespVO> getFlowLog(@RequestParam("id") Long id) {
        FlowLogDO flowLog = flowLogService.getFlowLog(id);
        return success(FlowLogConvert.INSTANCE.convert(flowLog));
    }

    @GetMapping("/list")
    @ApiOperation("获得列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:flow-log:query')")
    public CommonResult<List<FlowLogRespVO>> getFlowLogList(@RequestParam("ids") Collection<Long> ids) {
        List<FlowLogDO> list = flowLogService.getFlowLogList(ids);
        return success(FlowLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得分页")
    @PreAuthorize("@ss.hasPermission('system:flow-log:query')")
    public CommonResult<PageResult<FlowLogRespVO>> getFlowLogPage(@Valid FlowLogPageReqVO pageVO) {
        PageResult<FlowLogDO> pageResult = flowLogService.getFlowLogPage(pageVO);
        return success(FlowLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出 Excel")
    @PreAuthorize("@ss.hasPermission('system:flow-log:export')")
    @OperateLog(type = EXPORT)
    public void exportFlowLogExcel(@Valid FlowLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FlowLogDO> list = flowLogService.getFlowLogList(exportReqVO);
        // 导出 Excel
        List<FlowLogExcelVO> datas = FlowLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, ".xls", "数据", FlowLogExcelVO.class, datas);
    }

}
