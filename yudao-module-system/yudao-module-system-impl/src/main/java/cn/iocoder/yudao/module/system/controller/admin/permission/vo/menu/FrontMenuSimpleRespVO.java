package cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel("前台菜单精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontMenuSimpleRespVO {

    @ApiModelProperty(value = "编号", required = true)
    private Long id;

    @ApiModelProperty(value = "菜单名称", required = true, example = "芋道")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    @ApiModelProperty(value = "路由地址", example = "/contract", required = true)
    @Size(max = 1024, message = "路由地址不能超过1024个字符")
    private String path;

    @ApiModelProperty(value = "是否隐藏（0为否，1为是）", required = true)
    private Integer hideInMenu;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
    private Integer status;

}
