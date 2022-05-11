package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("报价单提交 Request VO")
@Data
@EqualsAndHashCode
@ToString
public class OfferSubmitReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

}
