package cn.iocoder.yudao.module.system.controller.admin.flow;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
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

import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.convert.flow.FlowConvert;
import cn.iocoder.yudao.module.system.service.flow.FlowService;

@Api(tags = "管理后台 - 项目流程")
@RestController
@RequestMapping("/system/flow")
@Validated
public class FlowController {

    @Resource
    private FlowService flowService;

    @PutMapping("/update")
    @ApiOperation("更新项目流程")
    public CommonResult<Boolean> updateFlow(@Valid @RequestBody FlowUpdateReqVO updateReqVO) {
        flowService.updateFlow(updateReqVO);
        return success(true);
    }

    @GetMapping("/get/condition")
    @ApiOperation("根据条件获得项目流程")
    @ApiImplicitParam(name = "condition", value = "条件", required = true, dataTypeClass = Map.class)
    public CommonResult<List<FlowRespVO>> getFlowsByCondition(@RequestParam Map<String, Object> condition) {
        List<FlowDO> flows = flowService.getFlowsByCondition(condition);
        return success(FlowConvert.INSTANCE.convertList(flows));
    }

    @GetMapping("/get/all")
    @ApiOperation("获得全部项目流程")
    public CommonResult<List<FlowRespVO>> getAllFlows() {
        List<FlowDO> flows = flowService.getAllFlows();
        return success(FlowConvert.INSTANCE.convertList(flows));
    }

    @GetMapping("/list")
    @ApiOperation("获得项目流程列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<FlowRespVO>> getFlowList(@RequestParam("ids") Collection<Long> ids) {
        List<FlowDO> list = flowService.getFlowList(ids);
        return success(FlowConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得项目流程分页")
    public CommonResult<PageResult<FlowRespVO>> getFlowPage(@Valid FlowPageReqVO pageVO) {
        PageResult<FlowDO> pageResult = flowService.getFlowPage(pageVO);
        return success(FlowConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出项目流程 Excel")
    @OperateLog(type = EXPORT)
    public void exportFlowExcel(@Valid FlowExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FlowDO> list = flowService.getFlowList(exportReqVO);
        // 导出 Excel
        List<FlowExcelVO> datas = FlowConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "项目流程.xls", "数据", FlowExcelVO.class, datas);
    }

}
