package cn.iocoder.yudao.module.system.controller.admin.solution;

import cn.iocoder.yudao.module.system.controller.admin.sample.vo.SampleCreateReqVO;
import com.alibaba.fastjson.JSONObject;
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

import cn.iocoder.yudao.module.system.controller.admin.solution.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.module.system.convert.solution.SolutionConvert;
import cn.iocoder.yudao.module.system.service.solution.SolutionService;

@Api(tags = "测试方案")
@RestController
@RequestMapping("/system/solution")
@Validated
public class SolutionController {

    @Resource
    private SolutionService solutionService;

    @GetMapping("/get/table6")
    @ApiOperation(value = "获得软件测试方案表格",
            notes = "需要填写id字段。其中id为表格编号，从合同的返回值中获取。返回值为json格式，存放在data字段中，包含表格内容。")
    public CommonResult<JSONObject> getSolutionTable6(@RequestParam("id") String id) {
        return success(solutionService.getSolutionTable6(id));
    }

    @GetMapping("/get/table13")
    @ApiOperation(value = "获得测试方案评审表",
            notes = "需要填写id字段。其中id为表格编号，从合同的返回值中获取。返回值为json格式，存放在data字段中，包含表格内容。")
    public CommonResult<JSONObject> getSolutionTable13(@RequestParam("id") String id) {
        return success(solutionService.getSolutionTable13(id));
    }

    @GetMapping("/get")
    @ApiOperation(value = "获得测试方案", notes = "需要填写id字段。其中id为测试方案编号。返回值为测试方案信息。")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<SolutionRespVO> getSolution(@RequestParam("id") Long id) {
        SolutionDO solution = solutionService.getSolution(id);
        return success(SolutionConvert.INSTANCE.convert(solution));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得测试方案列表", notes = "根据需要填写多个测试方案id，获得所有测试方案。返回值为测试方案列表。")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<SolutionRespVO>> getSolutionList(@RequestParam("ids") Collection<Long> ids) {
        List<SolutionDO> list = solutionService.getSolutionList(ids);
        return success(SolutionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得测试方案分页",
            notes = "需要填写页码pageNo和每页条数pageSize，再根据需要填写其他查询字段。返回值为测试方案分页。")
    public CommonResult<PageResult<SolutionRespVO>> getSolutionPage(@Valid SolutionPageReqVO pageVO) {
        PageResult<SolutionDO> pageResult = solutionService.getSolutionPage(pageVO);
        return success(SolutionConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出测试方案 Excel")
    @OperateLog(type = EXPORT)
    public void exportSolutionExcel(@Valid SolutionExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<SolutionDO> list = solutionService.getSolutionList(exportReqVO);
        // 导出 Excel
        List<SolutionExcelVO> datas = SolutionConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "测试方案.xls", "数据", SolutionExcelVO.class, datas);
    }

}
