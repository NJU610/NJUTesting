package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("利用Code创建公司认证 Request VO")
@Data
@ToString(callSuper = true)
public class UserCompanyCreateByCodeReqVO {

    @ApiModelProperty(value = "用户编号，不传入时使用认证token得到用户id", required = false)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "公司代码，联系系统管理员获取，8位数字字母字符串", required = true)
    @NotNull(message = "公司代码不能为空")
    private String code;
}
