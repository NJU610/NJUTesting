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

@ApiModel("手机密码登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMobileLoginReqVO {
    @ApiModelProperty(value = "账号", required = true, example = "12345678989")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "密码", required = true, example = "buzhidao")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @ApiModelProperty(value = "验证码", required = true, example = "1024", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "验证码不能为空", groups = AuthLoginReqVO.CodeEnableGroup.class)
    private String code;

    @ApiModelProperty(value = "验证码的唯一标识", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62", notes = "验证码开启时，需要传递")
    @NotEmpty(message = "唯一标识不能为空", groups = AuthLoginReqVO.CodeEnableGroup.class)
    private String uuid;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}
}
