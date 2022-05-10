package cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("Bpm委托分配 Request VO")
@Data
public class BpmDelegationAssignReqVO extends BpmDelegationBaseReqVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

}
