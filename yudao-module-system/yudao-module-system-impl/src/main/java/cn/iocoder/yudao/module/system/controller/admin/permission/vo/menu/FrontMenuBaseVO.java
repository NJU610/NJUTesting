package cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
* 前台菜单 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FrontMenuBaseVO {

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

    @ApiModelProperty(value = "关联数组", required = true, example = "[\"111\",\"222\"]")
    private List<String> parentKeys;
}
