package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 用户公司关联 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class UserCompanyBaseVO {

    @ApiModelProperty(value = "用户编号", required = true)
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @ApiModelProperty(value = "公司编号", required = true)
    @NotNull(message = "公司编号不能为空")
    private Long companyId;

}
