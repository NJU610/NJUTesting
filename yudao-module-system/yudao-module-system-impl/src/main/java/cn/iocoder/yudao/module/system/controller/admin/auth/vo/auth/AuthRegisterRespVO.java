package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("账号注册 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegisterRespVO {

    @ApiModelProperty(value = "token。返回token作为认证，调用需要认证的接口时需要传入该参数", required = true, example = "4389dhkd93ujdasgudd32h2e")
    private String token;
}
