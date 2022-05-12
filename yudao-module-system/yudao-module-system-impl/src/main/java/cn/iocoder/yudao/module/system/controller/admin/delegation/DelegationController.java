package cn.iocoder.yudao.module.system.controller.admin.delegation;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "委托")
@RestController
@RequestMapping("/system/delegation")
@Validated
public class DelegationController {

    @Resource
    private DelegationService delegationService;

    @PostMapping("/create")
    @ApiOperation(value = "客户-创建委托",
            notes = "客户使用。创建新委托，需要填写委托名称name字段。返回值为委托的id。")
    public CommonResult<Long> createDelegation(@Valid @RequestBody DelegationCreateReqVO createReqVO) {
        return success(delegationService.createDelegation(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation(value = "客户-更新委托",
            notes = "客户使用。用来更新文档材料的url或委托的名称。只需要传入委托的id和要更新的字段，其他字段可以为空。返回值为是否更新成功")
    public CommonResult<Boolean> updateDelegation(@Valid @RequestBody DelegationUpdateReqVO updateReqVO) {
        delegationService.updateDelegation(updateReqVO);
        return success(true);
    }

    @PutMapping("/save/table2")
    @ApiOperation(value = "客户-保存软件项目委托测试申请表",
            notes = "客户使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveDelegationTable2(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveDelegationTable2(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/save/table3")
    @ApiOperation(value = "客户-保存委托测试软件功能列表",
            notes = "客户使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveDelegationTable3(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveDelegationTable3(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/save/table14")
    @ApiOperation(value = "测试部人员-保存软件文档评审表",
            notes = "测试部人员使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveDelegationTable14(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveDelegationTable14(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/submit")
    @ApiOperation(value = "客户-表格填写完毕，提交委托",
            notes = "客户使用。需要填写id字段，其中id为委托id。返回值为是否提交成功。需要前端检验数据是否填写完整")
    public CommonResult<Boolean> submitDelegation(@Valid @RequestBody DelegationSubmitReqVO submitVo) {
        delegationService.submitDelegation(submitVo);
        return success(true);
    }

    @PutMapping("/distribute/marketing")
    @ApiOperation(value = "市场部主管-分配委托给市场部人员",
            notes = "管理员使用。需要填写id和acceptorId字段，其中id为委托id，acceptorId为接收委托的市场部人员id。返回值为是否更新成功。")
    public CommonResult<Boolean> distributeDelegationMarketing(@Valid @RequestBody DelegationDistributeReqVO distributeReqVO) {
        delegationService.distributeDelegation2Mkt(distributeReqVO);
        return success(true);
    }

    @PutMapping("/distribute/testing")
    @ApiOperation(value = "测试部主管-分配委托给测试部人员",
            notes = "管理员使用。需要填写id和acceptorId字段，其中id为委托id，acceptorId为接收委托的测试部人员id。返回值为是否更新成功。")
    public CommonResult<Boolean> distributeDelegationTesting(@Valid @RequestBody DelegationDistributeReqVO distributeReqVO) {
        delegationService.distributeDelegation2Test(distributeReqVO);
        return success(true);
    }

    @PutMapping("/audit/success/marketing")
    @ApiOperation(value = "市场部人员-审核委托通过",
            notes = "市场部人员使用。需要填写id字段，其中id为委托id。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationSuccessMkt(@Valid @RequestBody DelegationAuditReqVO auditReqVO) {
        delegationService.auditDelegationSuccessMkt(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/success/testing")
    @ApiOperation(value = "测试部人员-审核委托通过",
            notes = "测试部人员使用。需要填写id字段，其中id为委托id。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationSuccessTest(@Valid @RequestBody DelegationAuditReqVO auditReqVO) {
        delegationService.auditDelegationSuccessTest(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/marketing")
    @ApiOperation(value = "市场部人员-审核委托不通过",
            notes = "市场部人员使用。需要填写id和reason字段，其中id为委托id，reason为拒绝原因。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationFailMkt(@Valid @RequestBody DelegationAuditReqVO auditReqVO) {
        delegationService.auditDelegationFailMkt(auditReqVO);
        return success(true);
    }

    @PutMapping("/audit/fail/testing")
    @ApiOperation(value = "测试部人员-审核委托不通过",
            notes = "测试部人员使用。需要填写id和reason字段，其中id为委托id，reason为拒绝原因。返回值为是否更新状态成功。")
    public CommonResult<Boolean> auditDelegationFailTest(@Valid @RequestBody DelegationAuditReqVO auditReqVO) {
        delegationService.auditDelegationFailTest(auditReqVO);
        return success(true);
    }

    @PutMapping("/offer/save")
    @ApiOperation(value = "市场部人员-保存报价单",
            notes = "市场部人员使用。需要填写delegationId和data字段，其中delegationId为委托id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveOffer(@Valid @RequestBody DelegationSaveTableReqVO saveTableReqVo) {
        delegationService.saveOffer(saveTableReqVo);
        return success(true);
    }

    @PutMapping("/offer/submit")
    @ApiOperation(value = "市场部人员-提交报价单",
            notes = "市场部人员使用。需要填写id和data字段，其中id为报价单id，data是json格式，包含报价单内容。返回值为是否更新成功。")
    public CommonResult<Boolean> submitOffer(@Valid @RequestBody OfferSubmitReqVO offerSubmitReqVO) {
        delegationService.submitOffer(offerSubmitReqVO);
        return success(true);
    }

    @PutMapping("/offer/reject")
    @ApiOperation(value = "客户-不接受报价",
            notes = "客户使用。需要填写delegationId和reason字段，其中delegationId为委托id，reason为不接受原因。返回值为是否更新成功。")
    public CommonResult<Boolean> rejectOffer(@Valid @RequestBody OfferRejectReqVO offerRejectReqVO) {
        delegationService.rejectOffer(offerRejectReqVO);
        return success(true);
    }

    @PutMapping("/offer/accept")
    @ApiOperation(value = "客户-接受报价",
            notes = "客户使用。需要填写delegationId字段，其中delegationId为委托id。返回值为是否更新成功。")
    public CommonResult<Boolean> acceptOffer(@Valid @RequestBody OfferAcceptReqVO offerAcceptReqVO) {
        delegationService.acceptOffer(offerAcceptReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "根据id删除委托",
            notes = "需要填写id字段。其中id为委托id。会自动删除对应的流程和表格。返回值为是否删除成功")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteDelegation(@RequestParam("id") Long id) {
        delegationService.deleteDelegation(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据id获得委托",
            notes = "需要填写id字段。其中id为委托id。返回值为委托信息，包含委托当前状态。")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<DelegationRespVO> getDelegation(@RequestParam("id") Long id) {
        DelegationDO delegation = delegationService.getDelegation(id);
        return success(DelegationConvert.INSTANCE.convert(delegation));
    }

    @GetMapping("/get/current-user")
    @ApiOperation(value = "获得当前用户的所有委托",
            notes = "用户使用。获得当前用户所有未被删除的委托。返回值为委托列表，包含委托当前状态。")
    public CommonResult<List<DelegationRespVO>> getDelegationByCurrentUser() {
        List<DelegationDO> delegations = delegationService.getDelegationsByCurrentUser();
        return success(DelegationConvert.INSTANCE.convertList(delegations));
    }

    @GetMapping("/get/creator-id")
    @ApiOperation(value = "根据用户ID获得特定用户的所有委托",
            notes = "管理员使用。需要填写id字段。其中id为用户id。返回值为委托列表，包含委托当前状态。")
    @ApiImplicitParam(name = "id", value = "用户编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<List<DelegationRespVO>> getDelegationByCreator(@RequestParam("id") Long id) {
        List<DelegationDO> delegations = delegationService.getDelegationsByCreator(id);
        return success(DelegationConvert.INSTANCE.convertList(delegations));
    }

    @GetMapping("/get/not-accepted")
    @ApiOperation(value = "获得未被接收的所有委托",
            notes = "管理员使用。获得所有未被接收的委托。返回值为委托列表，包含委托当前状态。")
    public CommonResult<List<DelegationRespVO>> getDelegationNotAccepted() {
        List<DelegationDO> delegations = delegationService.getDelegationsNotAccepted();
        return success(DelegationConvert.INSTANCE.convertList(delegations));
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

    @GetMapping("/get/table14")
    @ApiOperation(value = "获得软件文档评审表",
            notes = "管理员使用。需要填写id字段。其中id为表格id，从委托的返回值中获取。返回值为json格式，存放在data字段中，包含表格内容。")
    @ApiImplicitParam(name = "id", value = "表格编号", required = true, dataTypeClass = String.class)
    public CommonResult<String> getDelegationTable14(@RequestParam("id") String id) {
        return success(delegationService.getDelegationTable14(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得委托列表",
            notes = "管理员使用。根据需要填写多个委托id，获得所有委托。返回值为委托列表，包含委托当前状态。")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<DelegationRespVO>> getDelegationList(@RequestParam("ids") Collection<Long> ids) {
        List<DelegationDO> list = delegationService.getDelegationList(ids);
        return success(DelegationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获得委托分页",
            notes = "管理员和用户使用。需要填写页码pageNo和每页条数pageSize，再根据需要填写其他查询字段。返回值为委托分页，包含委托当前状态。")
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
