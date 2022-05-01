package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 公司 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class CompanyBaseVO {

    @ApiModelProperty(value = "公司名称", required = true)
    @NotNull(message = "公司名称不能为空")
    private String name;

    @ApiModelProperty(value = "公司地址", required = true)
    @NotNull(message = "公司地址不能为空")
    private String address;

    @ApiModelProperty(value = "公司联系方式", required = true)
    @NotNull(message = "公司联系方式不能为空")
    private String phone;

    @ApiModelProperty(value = "公司编码，认证公司时使用， 非必要时无需传入，后台自动生成", required = false)
    private String code;

}
