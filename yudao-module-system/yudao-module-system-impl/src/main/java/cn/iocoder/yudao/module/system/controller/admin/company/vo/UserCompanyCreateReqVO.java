package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import io.swagger.annotations.*;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 用户公司关联创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCompanyCreateReqVO extends UserCompanyBaseVO {
    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "公司编号", required = true)
    @NotNull(message = "公司编号不能为空")
    private Long companyId;
}
