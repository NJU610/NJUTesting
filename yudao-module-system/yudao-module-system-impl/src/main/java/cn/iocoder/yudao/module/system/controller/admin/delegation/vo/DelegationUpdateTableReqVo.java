package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 委托表格更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationUpdateTableReqVo extends DelegationTableBaseVo {

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private String id;

}
