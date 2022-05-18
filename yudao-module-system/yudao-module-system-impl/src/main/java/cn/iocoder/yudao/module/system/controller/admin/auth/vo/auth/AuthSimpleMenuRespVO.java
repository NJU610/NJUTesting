package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("登录用户简化菜单信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSimpleMenuRespVO {

    @ApiModelProperty(value = "菜单名称", required = true, example = "用户管理")
    private String name;

    @ApiModelProperty(value = "路由地址", example = "/delegation", notes = "路由地址")
    private String path;

    @ApiModelProperty(value = "路由参数", example = "1", notes = "路由参数")
    private Integer hideInMenu;
}
