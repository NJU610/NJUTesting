package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("公司更新 Request VO")
@Data
@ToString(callSuper = true)
public class CompanyUpdateReqVO{

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "公司地址")
    private String address;

    @ApiModelProperty(value = "公司联系方式")
    private String phone;

    @ApiModelProperty(value = "公司编码，认证公司时使用")
    private String code;

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private Long id;

}
