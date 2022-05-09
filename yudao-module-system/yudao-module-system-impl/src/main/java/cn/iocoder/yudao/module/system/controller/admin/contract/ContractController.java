package cn.iocoder.yudao.module.system.controller.admin.contract;

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

import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.convert.contract.ContractConvert;
import cn.iocoder.yudao.module.system.service.contract.ContractService;

@Api(tags = "合同")
@RestController
@RequestMapping("/system/contract")
@Validated
public class ContractController {

    @Resource
    private ContractService contractService;

    @PostMapping("/create")
    @ApiOperation(value = "创建合同",
            notes = "市场部工作人员使用。需要填写delegationId字段。其中delegationId为委托id。返回值为合同的id。")
    public CommonResult<Long> createContract(@RequestBody @Validated ContractCreateReqVO createReqVO) {
        return success(contractService.createContract(createReqVO));
    }

    @PutMapping("/save/table4")
    @ApiOperation(value = "保存软件委托测试合同",
            notes = "客户和市场部人员使用。需要填写contractId和data字段，其中contractId为合同id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveContractTable4(@Valid @RequestBody ContractSaveTableReqVO saveReqVO) {
        contractService.saveContractTable4(saveReqVO);
        return success(true);
    }

    @PutMapping("/save/table5")
    @ApiOperation(value = "保存软件项目委托测试保密协议",
            notes = "客户和市场部人员使用。需要填写contractId和data字段，其中contractId为合同id，data是json格式，包含表格内容。返回值为是否更新成功")
    public CommonResult<Boolean> saveContractTable5(@Valid @RequestBody ContractSaveTableReqVO saveReqVO) {
        contractService.saveContractTable5(saveReqVO);
        return success(true);
    }

    @PutMapping("/submit/staff")
    @ApiOperation(value = "市场部人员提交合同",
            notes = "市场部人员使用。需要填写id字段。其中id为合同id。返回值为是否保存成功。需要前端检验数据是否填写完整")
    public CommonResult<Boolean> submitContractStaff(@Valid @RequestBody ContractSubmitReqVO submitReqVO) {
        contractService.submitContractStaff(submitReqVO);
        return success(true);
    }

    @PutMapping("/reject/client")
    @ApiOperation(value = "客户不接受市场部合同草稿",
            notes = "客户使用。需要填写id和reason字段。其中id为合同id，reason为拒绝原因。返回值为是否更新成功。")
    public CommonResult<Boolean> rejectContractClient(@Valid @RequestBody ContractRejectReqVO rejectReqVO) {
        contractService.rejectContractClient(rejectReqVO);
        return success(true);
    }

    @PutMapping("/submit/client")
    @ApiOperation(value = "客户接受市场部合同草稿，并提交合同草稿",
            notes = "客户使用。需要填写id字段。其中id为合同id。返回值为是否保存成功。需要前端检验数据是否填写完整")
    public CommonResult<Boolean> submitContractClient(@Valid @RequestBody ContractSubmitReqVO submitReqVO) {
        contractService.submitContractClient(submitReqVO);
        return success(true);
    }

    @PutMapping("/reject/staff")
    @ApiOperation(value = "市场部审核合同不通过",
            notes = "市场部人员使用。需要填写id和reason字段。其中id为合同id，reason为拒绝原因。返回值为是否更新成功。")
    public CommonResult<Boolean> rejectContractStaff(@Valid @RequestBody ContractRejectReqVO rejectReqVO) {
        contractService.rejectContractStaff(rejectReqVO);
        return success(true);
    }

    @PutMapping("/accept/staff")
    @ApiOperation(value = "市场部审核合同通过",
            notes = "市场部人员使用。需要填写id字段。其中id为合同id。返回值为是否更新成功。")
    public CommonResult<Boolean> acceptContractStaff(@Valid @RequestBody ContractAcceptReqVO acceptReqVO) {
        contractService.acceptContractStaff(acceptReqVO);
        return success(true);
    }

    @PutMapping("/upload/doc")
    @ApiOperation(value = "上传实体合同材料的url"
            , notes = "测试人员使用，上传后更新状态为合同签署完毕，用户发送样品中。需要填写id和url字段。其中id为合同id，url为实体合同材料的url。返回值为是否保存成功。")
    public CommonResult<Boolean> updateContract(@Valid @RequestBody ContractUploadDocReqVO uploadDocReqVO) {
        contractService.uploadDocument(uploadDocReqVO);
        return success(true);
    }

    /*
    @DeleteMapping("/delete")
    @ApiOperation("删除合同")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteContract(@RequestParam("id") Long id) {
        contractService.deleteContract(id);
        return success(true);
    }
    */

    @GetMapping("/get")
    @ApiOperation("获得合同")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<ContractRespVO> getContract(@RequestParam("id") Long id) {
        ContractDO contract = contractService.getContract(id);
        return success(ContractConvert.INSTANCE.convert(contract));
    }

    @GetMapping("/list")
    @ApiOperation("获得合同列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<ContractRespVO>> getContractList(@RequestParam("ids") Collection<Long> ids) {
        List<ContractDO> list = contractService.getContractList(ids);
        return success(ContractConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得合同分页")
    public CommonResult<PageResult<ContractRespVO>> getContractPage(@Valid ContractPageReqVO pageVO) {
        PageResult<ContractDO> pageResult = contractService.getContractPage(pageVO);
        return success(ContractConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出合同 Excel")
    @OperateLog(type = EXPORT)
    public void exportContractExcel(@Valid ContractExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ContractDO> list = contractService.getContractList(exportReqVO);
        // 导出 Excel
        List<ContractExcelVO> datas = ContractConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "合同.xls", "数据", ContractExcelVO.class, datas);
    }

}
