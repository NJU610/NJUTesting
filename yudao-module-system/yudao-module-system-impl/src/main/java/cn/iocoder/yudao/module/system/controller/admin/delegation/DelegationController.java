package cn.iocoder.yudao.module.system.controller.admin.delegation;

import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.flow.FlowService;
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

import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;

@Api(tags = "委托")
@RestController
@RequestMapping("/system/delegation")
@Validated
public class DelegationController {

    @Resource
    private DelegationService delegationService;

    @Resource
    private FlowService flowService;

    @Resource
    private ContractService contractService;

    @PostMapping("/create")
    @ApiOperation(value = "创建委托",
            notes = "用户使用。创建新委托，需要填写委托名称name字段。返回值为委托的id。")
    public CommonResult<Long> createDelegation(@Valid @RequestBody DelegationCreateReqVO createReqVO) {
        return success(delegationService.createDelegation(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新委托",
            notes = "用户使用。只需要传入委托的id和要更新的字段，其他字段可以为空。返回值为是否更新成功")
    public CommonResult<Boolean> updateDelegation(@Valid @RequestBody DelegationUpdateReqVO updateReqVO) {
        delegationService.updateDelegation(updateReqVO);
        return success(true);
    }

    @PutMapping("/save/table2")
    @ApiOperation(value = "保存软件项目委托测试申请表",
            notes = "用户使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveDelegationTable2(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveDelegationTable2(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/save/table3")
    @ApiOperation(value = "保存委托测试软件功能列表",
            notes = "用户使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveDelegationTable3(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveDelegationTable3(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/submit")
    @ApiOperation(value = "表格填写完毕，提交委托",
            notes = "用户使用。需要填写id字段，其中id为委托id。返回值为是否提交成功。需要前端检验数据是否填写完整")
    public CommonResult<Boolean> submitDelegation(@Valid @RequestBody DelegationSubmitReqVO submitVo) {
        delegationService.submitDelegation(submitVo);
        return success(true);
    }

    @PutMapping("/distribute")
    @ApiOperation(value = "分配委托给测试人员",
            notes = "管理员使用。需要填写id和acceptorId字段，其中id为委托id，acceptorId为测试人员id。返回值为是否更新成功。")
    public CommonResult<Boolean> distributeDelegation(@Valid @RequestBody DelegationDistributeReqVO distributeReqVO) {
        delegationService.distributeDelegation(distributeReqVO);
        return success(true);
    }

    @PutMapping("/accept")
    @ApiOperation(value = "接受委托",
            notes = "测试人员使用。需要填写id字段，其中id为委托id。返回值为是否接收成功。")
    public CommonResult<Boolean> acceptDelegation(@Valid @RequestBody DelegationAcceptReqVO acceptReqVO) {
        delegationService.acceptDelegation(acceptReqVO);
        return success(true);
    }

    @PutMapping("/audit/success")
    @ApiOperation(value = "委托审核通过",
            notes = "测试人员使用。需要填写id字段，其中id为委托id。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationSuccess(@Valid @RequestBody DelegationAcceptReqVO acceptReqVO) {
        delegationService.auditDelegationSuccess(acceptReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail")
    @ApiOperation(value = "委托审核不通过",
            notes = "测试人员使用。需要填写id字段，其中id为委托id。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationFail(@Valid @RequestBody DelegationAcceptReqVO acceptReqVO) {
        delegationService.auditDelegationFail(acceptReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "根据id删除委托",
            notes = "用户使用。需要填写id字段。其中id为委托id。会自动删除对应的流程和表格。返回值为是否删除成功")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteDelegation(@RequestParam("id") Long id) {
        delegationService.deleteDelegation(id);

        // 删除flow
        flowService.deleteFlowByDelegation(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据id获得委托",
            notes = "管理员使用。需要填写id字段。其中id为委托id。返回值为委托信息，包含委托当前状态。")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<DelegationRespVO> getDelegation(@RequestParam("id") Long id) {
        DelegationRespVO delegation = delegationService.getDelegation(id);
        return success(delegation);
    }

    @GetMapping("/get/current-user")
    @ApiOperation(value = "获得当前用户的所有委托",
            notes = "用户使用。获得当前用户所有未被删除的委托。返回值为委托列表，包含委托当前状态。")
    public CommonResult<List<DelegationRespVO>> getDelegationByCurrentUser() {
        List<DelegationRespVO> delegations = delegationService.getDelegationsByCurrentUser();
        return success(delegations);
    }

    @GetMapping("/get/creator-id")
    @ApiOperation(value = "根据用户ID获得特定用户的所有委托",
            notes = "管理员使用。需要填写id字段。其中id为用户id。返回值为委托列表，包含委托当前状态。")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<List<DelegationRespVO>> getDelegationByCreator(@RequestParam("id") Long id) {
        List<DelegationRespVO> delegations = delegationService.getDelegationsByCreator(id);
        return success(delegations);
    }

    @GetMapping("/get/not-accepted")
    @ApiOperation(value = "获得未被接收的所有委托",
            notes = "管理员使用。获得所有未被接收的委托。返回值为委托列表，包含委托当前状态。")
    public CommonResult<List<DelegationRespVO>> getDelegationNotAccepted() {
        List<DelegationRespVO> delegations = delegationService.getDelegationsNotAccepted();
        return success(delegations);
    }

    @GetMapping("/get/table2")
    @ApiOperation(value = "获得软件项目委托测试申请表",
            notes = "用户使用。需要填写id字段。其中id为表格id，从委托的返回值中获取。返回值为json格式，存放在data字段中，包含表格内容。")
    @ApiImplicitParam(name = "id", value = "表格编号", required = true, dataTypeClass = String.class)
    public CommonResult<String> getDelegationTable2(@RequestParam("id") String id) {
        return success(delegationService.getDelegationTable2(id));
    }

    @GetMapping("/get/table3")
    @ApiOperation(value = "获得委托测试软件功能列表",
            notes = "用户使用。需要填写id字段。其中id为表格id，从委托的返回值中获取。返回值为json格式，存放在data字段中，包含表格内容。")
    @ApiImplicitParam(name = "id", value = "表格编号", required = true, dataTypeClass = String.class)
    public CommonResult<String> getDelegationTable3(@RequestParam("id") String id) {
        return success(delegationService.getDelegationTable3(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得委托列表",
            notes = "管理员使用。根据需要填写多个委托id，获得所有委托。返回值为委托列表，包含委托当前状态。")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<DelegationRespVO>> getDelegationList(@RequestParam("ids") Collection<Long> ids) {
        List<DelegationRespVO> list = delegationService.getDelegationList(ids);
        return success(list);
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得委托分页",
            notes = "管理员和用户使用。需要填写页码pageNo和每页条数pageSize，再根据需要填写其他查询字段。返回值为委托分页，包含委托当前状态。")
    public CommonResult<PageResult<DelegationRespVO>> getDelegationPage(@Valid DelegationPageReqVO pageVO) {
        PageResult<DelegationRespVO> pageResult = delegationService.getDelegationPage(pageVO);
        return success(pageResult);
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
