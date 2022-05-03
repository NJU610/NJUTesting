package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.framework.common.validation.Mobile;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("发送手机验证码 Response VO")
@Data
@Accessors(chain = true)
public class AuthSendSmsReqVO {

    @ApiModelProperty(value = "手机号码。不带地区前缀，为11位", required = true, example = "12345678989")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "发送场景。4为登录验证码，5为忘记密码验证码,6为注册验证码", required = true, example = "4", notes = "4为登录验证码，5为忘记密码验证码,6为注册验证码")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}

