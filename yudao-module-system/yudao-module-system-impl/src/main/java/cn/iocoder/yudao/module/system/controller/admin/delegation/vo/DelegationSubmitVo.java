package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("委托提交 Request VO")
@Data
public class DelegationSubmitVo {

    @ApiModelProperty(value = "编号", example = "1", required = true)
    @NotNull(message = "编号不能为空")
    private Long id;

}
