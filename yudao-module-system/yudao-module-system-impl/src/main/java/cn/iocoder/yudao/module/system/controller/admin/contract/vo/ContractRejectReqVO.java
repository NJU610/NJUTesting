package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("合同审核不通过 Request VO")
@Data
@EqualsAndHashCode
@ToString
public class ContractRejectReqVO {

    @ApiModelProperty(value = "合同编号", example = "1", required = true)
    @NotNull(message = "合同编号不能为空")
    private Long id;

    @ApiModelProperty(value = "审核不通过原因", example = "审核不通过原因", required = true)
    @NotNull(message = "审核不通过原因不能为空")
    private String reason;

}
