package cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("Bpm委托审核 Request VO")
@Data
public class BpmDelegationAuditReqVO extends BpmDelegationBaseReqVO {

    @ApiModelProperty(value = "审核结果", required = true, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean result;

    @ApiModelProperty(value = "审核意见", required = true, example = "审核备注")
    @NotNull(message = "审核意见不能为空")
    private String remark;

}
