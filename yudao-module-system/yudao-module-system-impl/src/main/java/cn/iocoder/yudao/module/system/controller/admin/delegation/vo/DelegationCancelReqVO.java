package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("委托取消 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationCancelReqVO extends DelegationEditBaseVO {

    @ApiModelProperty(value = "取消原因", example = "blabla", required = true)
    @NotNull(message = "取消原因不能为空")
    private String remark;

}
