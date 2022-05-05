package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("合同创建 Request VO")
@Data
@ToString
public class ContractCreateReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

    @ApiModelProperty(value = "发起者编号", example = "1")
    private Long creatorId;

}
