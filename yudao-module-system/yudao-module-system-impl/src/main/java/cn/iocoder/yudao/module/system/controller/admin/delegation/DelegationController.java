package cn.iocoder.yudao.module.system.controller.admin.delegation;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowCreateVO;
import cn.iocoder.yudao.module.system.service.flow.FlowService;
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

import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;

@Api(tags = "管理后台 - 委托")
@RestController
@RequestMapping("/system/delegation")
@Validated
public class DelegationController {

    @Resource
    private DelegationService delegationService;

    @Resource
    private FlowService flowService;

    @PostMapping("/create")
    @ApiOperation("创建委托")
    public CommonResult<Long> createDelegation(@Valid @RequestBody DelegationCreateReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        createReqVO.setCreatorId(loginUserId);
        Long delegationId = delegationService.createDelegation(createReqVO);

        // 创建flow
        FlowCreateVO flowCreateVO = FlowCreateVO.builder()
                .delegationId(delegationId)
                .creatorId(loginUserId)
                .launchTime(createReqVO.getLaunchTime())
                .build();
        flowService.createFlow(flowCreateVO);
        return success(delegationId);
    }

    @PostMapping("/create/table")
    @ApiOperation("创建软件项目委托测试申请表、委托测试软件功能列表")
    public CommonResult<String> createDelegationTable(@Valid @RequestBody DelegationCreateTableReqVo createTableReqVo) {
        return success(delegationService.createDelegationTable(createTableReqVo));
    }

    @PutMapping("/update")
    @ApiOperation("更新委托")
    public CommonResult<Boolean> updateDelegation(@Valid @RequestBody DelegationUpdateReqVO updateReqVO) {
        delegationService.updateDelegation(updateReqVO);
        return success(true);
    }

    @PutMapping("/update/table")
    @ApiOperation("更新委托表格")
    public CommonResult<Boolean> updateDelegationTable(@Valid @RequestBody DelegationUpdateTableReqVo updateTableReqVo) {
        delegationService.updateDelegationTable(updateTableReqVo);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除委托")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteDelegation(@RequestParam("id") Long id) {
        delegationService.deleteDelegation(id);

        // 删除flow
        flowService.deleteFlowByCreator(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("根据ID获得委托")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<DelegationRespVO> getDelegation(@RequestParam("id") Long id) {
        DelegationDO delegation = delegationService.getDelegation(id);
        return success(DelegationConvert.INSTANCE.convert(delegation));
    }

    @GetMapping("/get/current-user")
    @ApiOperation("获得当前用户的所有委托")
    public CommonResult<List<DelegationRespVO>> getDelegationByCurrentUser() {
        List<DelegationDO> delegations = delegationService.getDelegationsByCurrentUser();
        List<DelegationRespVO> delegationRespVOS = DelegationConvert.INSTANCE.convertList(delegations);
        return success(delegationRespVOS);
    }

    @GetMapping("/get/creator")
    @ApiOperation("根据用户ID获得特定用户的所有委托")
    public CommonResult<List<DelegationRespVO>> getDelegationByCreator(@RequestParam("id") Long id) {
        List<DelegationDO> delegations = delegationService.getDelegationsByCreator(id);
        List<DelegationRespVO> delegationRespVOS = DelegationConvert.INSTANCE.convertList(delegations);
        return success(delegationRespVOS);
    }

    @GetMapping("/get/not-accepted")
    @ApiOperation("获得未被接收的所有委托")
    public CommonResult<List<DelegationRespVO>> getDelegationNotAccepted() {
        List<DelegationDO> delegations = delegationService.getDelegationsNotAccepted();
        List<DelegationRespVO> delegationRespVOS = DelegationConvert.INSTANCE.convertList(delegations);
        return success(delegationRespVOS);
    }

    @GetMapping("/get/table")
    @ApiOperation("获得软件项目委托测试申请表、委托测试软件功能列表")
    public CommonResult<String> getDelegationTable(@RequestParam("id") String id, @RequestParam("table") String table) {
        return success(delegationService.getDelegationTable(id, table));
    }

    @GetMapping("/list")
    @ApiOperation("获得委托列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<DelegationRespVO>> getDelegationList(@RequestParam("ids") Collection<Long> ids) {
        List<DelegationDO> list = delegationService.getDelegationList(ids);
        return success(DelegationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得委托分页")
    public CommonResult<PageResult<DelegationRespVO>> getDelegationPage(@Valid DelegationPageReqVO pageVO) {
        PageResult<DelegationDO> pageResult = delegationService.getDelegationPage(pageVO);
        return success(DelegationConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出委托 Excel")
    @OperateLog(type = EXPORT)
    public void exportDelegationExcel(@Valid DelegationExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DelegationDO> list = delegationService.getDelegationList(exportReqVO);
        // 导出 Excel
        List<DelegationExcelVO> datas = DelegationConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "委托.xls", "数据", DelegationExcelVO.class, datas);
    }

}
