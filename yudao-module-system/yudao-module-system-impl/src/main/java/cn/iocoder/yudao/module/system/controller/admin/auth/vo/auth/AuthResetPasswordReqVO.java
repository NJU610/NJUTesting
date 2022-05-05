package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;

import cn.iocoder.yudao.framework.common.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel("重置密码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResetPasswordReqVO {

    @ApiModelProperty(value = "新密码。密码为8-16位，包含数字及字母", required = true, example = "password123")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @ApiModelProperty(value = "手机验证码。提前调用’/send-sms-code‘接口获得验证码，验证码为4位数字", required = true, example = "1024")
    @NotEmpty(message = "手机验证码不能为空")
    @Length(min = 4, max = 4, message = "手机验证码长度为 4 位")
    @Pattern(regexp = "^[0-9]+$", message = "手机验证码必须都是数字")
    private String code;

    @ApiModelProperty(value = "手机号码。不带地区前缀，为11位", required = true, example = "12345678989")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

}
