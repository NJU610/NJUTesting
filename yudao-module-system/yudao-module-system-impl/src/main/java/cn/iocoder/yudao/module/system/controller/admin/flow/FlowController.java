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

@Api(tags = "项目流程")
@RestController
@RequestMapping("/system/flow")
@Validated
public class FlowController {

    @Resource
    private FlowService flowService;

    @PutMapping("/update")
    @ApiOperation(value = "根据id更新项目流程",
            notes = "管理员使用。需要填写流程id和要更新的字段，返回值为是否更新成功")
    public CommonResult<Boolean> updateFlow(@Valid @RequestBody FlowUpdateReqVO updateReqVO) {
        flowService.updateFlow(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据id获得项目流程",
            notes = "管理员使用。需要传入流程id，返回值为对应流程信息")
    @ApiImplicitParam(name = "id", value = "流程编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<FlowRespVO> getFlow(@RequestParam("id") Long id) {
        FlowRespVO flowRespVO = FlowConvert.INSTANCE.convert(flowService.getFlow(id));
        return success(flowRespVO);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "获得全部项目流程",
            notes = "管理员使用。返回值为全部项目流程列表")
    public CommonResult<List<FlowRespVO>> getAllFlows() {
        List<FlowDO> flows = flowService.getAllFlows();
        return success(FlowConvert.INSTANCE.convertList(flows));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得项目流程列表",
            notes = "管理员使用。根据需要传入多个流程id，返回值为项目流程列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<FlowRespVO>> getFlowList(@RequestParam("ids") Collection<Long> ids) {
        List<FlowDO> list = flowService.getFlowList(ids);
        return success(FlowConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得项目流程分页",
            notes = "管理员使用。需要填写页码pageNo和每页条数pageSize，再根据需要填写其他查询字段。返回值为项目流程分页")
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
