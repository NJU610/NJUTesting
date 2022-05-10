package cn.iocoder.yudao.module.bpm.controller.admin.delegation;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAuditReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAssignReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationSubmitReqVO;
import cn.iocoder.yudao.module.bpm.service.delegation.BpmDelegationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Api(tags = "Bpm委托")
@RestController
@RequestMapping("/bpm/delegation")
@Validated
public class BpmDelegationController {

    @Resource
    private BpmDelegationService bpmDelegationService;

    @PostMapping("/create")
    @ApiOperation("新建委托")
    public CommonResult<String> createDelegation(@Validated @RequestBody BpmDelegationCreateReqVO createReqVO) {
        return success(bpmDelegationService.createDelegation(createReqVO));
    }

    @PutMapping("/submit")
    @ApiOperation(value = "表格填写完毕，提交委托",
            notes = "用户使用。需要填写id字段，其中id为任务id。返回值为是否提交成功。需要前端检验数据是否填写完整")
    public CommonResult<Boolean> submitDelegation(@Valid @RequestBody BpmDelegationSubmitReqVO submitVo) {
        bpmDelegationService.submitDelegation(submitVo);
        return success(true);
    }

    @PutMapping("/distribute/marketing")
    @ApiOperation(value = "分配委托给市场部人员",
            notes = "管理员使用。需要填写id和acceptorId字段，其中id为委托id，acceptorId为接收委托的市场部人员id。返回值为是否更新成功。")
    public CommonResult<Boolean> assignDelegationMarketing(@Valid @RequestBody BpmDelegationAssignReqVO assignReqVO) {
        bpmDelegationService.assignDelegation(assignReqVO, "marketing");
        return success(true);
    }

    @PutMapping("/distribute/testing")
    @ApiOperation(value = "分配委托给测试部人员",
            notes = "管理员使用。需要填写id和acceptorId字段，其中id为委托id，acceptorId为接收委托的测试部人员id。返回值为是否更新成功。")
    public CommonResult<Boolean> assignDelegationTesting(@Valid @RequestBody BpmDelegationAssignReqVO assignReqVO) {
        bpmDelegationService.assignDelegation(assignReqVO, "testing");
        return success(true);
    }

    @PutMapping("/audit/marketing")
    @ApiOperation(value = "市场部人员审核委托",
            notes = "市场部人员使用。")
    public CommonResult<Boolean> auditDelegationMarketing(@Valid @RequestBody BpmDelegationAuditReqVO auditReqVO) {
        bpmDelegationService.auditDelegation(auditReqVO);
        return success(true);
    }


}
