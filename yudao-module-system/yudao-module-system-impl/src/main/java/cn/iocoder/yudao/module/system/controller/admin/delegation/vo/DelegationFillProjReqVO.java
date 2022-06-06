package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("委托填写项目编号 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationFillProjReqVO extends DelegationEditBaseVO {

    @ApiModelProperty(value = "项目编号", example = "abc", required = true)
    @NotNull(message = "项目编号不能为空")
    private String projectId;

}
