package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("公司创建 Request VO")
@Data
@ToString(callSuper = true)
public class CompanyCreateReqVO{
    @ApiModelProperty(value = "公司名称", required = true)
    @NotNull(message = "公司名称不能为空")
    private String name;

    @ApiModelProperty(value = "公司地址", required = true)
    @NotNull(message = "公司地址不能为空")
    private String address;

    @ApiModelProperty(value = "公司联系方式", required = true)
    @NotNull(message = "公司联系方式不能为空")
    private String phone;

    @ApiModelProperty(value = "公司编码，认证公司时使用， 非必要时无需传入，后台自动生成，为8位字母数字", required = false)
    private String code;
}
