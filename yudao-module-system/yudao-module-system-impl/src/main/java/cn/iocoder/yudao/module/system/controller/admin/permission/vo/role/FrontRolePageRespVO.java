package cn.iocoder.yudao.module.system.controller.admin.permission.vo.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("角色分页 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FrontRolePageRespVO extends FrontRoleBaseVO{

    @ApiModelProperty(value = "角色编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

    @ApiModelProperty(value = "角色类型", required = true, example = "1", notes = "见 RoleTypeEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer type;

}
