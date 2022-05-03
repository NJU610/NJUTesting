package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel("账号密码登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginReqVO {

    @ApiModelProperty(value = "账号。账号为4-16位数字以及密码", required = true, example = "username")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 8, max = 16, message = "账号长度为 8-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(value = "密码。密码为8-16位，包含数字及字母", required = true, example = "password123")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 16, message = "密码长度为 8-16 位")
    private String password;

    @ApiModelProperty(value = "验证码。验证码开启时，需要传递", required = true, example = "1024", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "验证码不能为空", groups = CodeEnableGroup.class)
    private String code;

    @ApiModelProperty(value = "验证码的唯一标识。调用验证码接口后返回的uuid", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "唯一标识不能为空", groups = CodeEnableGroup.class)
    private String uuid;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}

}
