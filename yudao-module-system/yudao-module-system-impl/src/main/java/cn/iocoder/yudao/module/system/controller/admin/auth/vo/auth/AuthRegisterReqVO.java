package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;

import cn.iocoder.yudao.framework.common.validation.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel("账号注册 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterReqVO {

    @ApiModelProperty(value = "账号。账号为4-16位数字以及密码", required = true, example = "username")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 8, max = 16, message = "账号长度为 8-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(value = "用户昵称。用户昵称长度不能超过30个字符", required = true, example = "一只小白")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;


    @ApiModelProperty(value = "手机号码。不带地区前缀，为11位", required = true, example = "12345678989")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "密码。密码为8-16位，包含数字及字母", required = true, example = "password123")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度为 8-16 位")
    private String password;

    @ApiModelProperty(value = "手机验证码。提前调用’/send-sms-code‘接口获得验证码，验证码为4位数字", required = true, example = "1024")
    @NotEmpty(message = "手机验证码不能为空")
    @Length(min = 4, max = 4, message = "手机验证码长度为 4 位")
    @Pattern(regexp = "^[0-9]+$", message = "手机验证码必须都是数字")
    private String code;



}
