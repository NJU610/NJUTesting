package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("合同提交 Request VO")
@Data
public class ContractSubmitReqVO {

    @ApiModelProperty(value = "合同编号", example = "1", required = true)
    @NotNull(message = "合同编号不能为空")
    private Long id;

}
