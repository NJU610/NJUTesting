package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("用户公司关联更新 Request VO")
@Data
@ToString(callSuper = true)
public class UserCompanyUpdateReqVO{

    @ApiModelProperty(value = "用户编号", required = true)
    private Long userId;

    @ApiModelProperty(value = "公司编号", required = true)
    private Long companyId;
}
