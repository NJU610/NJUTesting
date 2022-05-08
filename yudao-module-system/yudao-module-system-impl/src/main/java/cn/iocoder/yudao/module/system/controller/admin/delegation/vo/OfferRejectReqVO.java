package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("客户不接受报价 Request VO")
@Data
@EqualsAndHashCode
@ToString
public class OfferRejectReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

    @ApiModelProperty(value = "原因", example = "example reason", required = true)
    @NotNull(message = "原因不能为空")
    private String reason;

}
