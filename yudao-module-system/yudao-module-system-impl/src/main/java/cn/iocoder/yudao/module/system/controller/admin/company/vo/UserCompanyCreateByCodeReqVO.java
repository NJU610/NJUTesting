package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 利用Code创建公司认证 Request VO")
@Data
@ToString(callSuper = true)
public class UserCompanyCreateByCodeReqVO {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "公司代码", required = true)
    @NotNull(message = "公司代码不能为空")
    private String code;
}
