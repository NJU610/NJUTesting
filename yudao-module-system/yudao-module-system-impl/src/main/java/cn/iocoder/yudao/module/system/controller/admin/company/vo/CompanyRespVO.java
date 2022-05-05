package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import javax.validation.constraints.NotNull;

@ApiModel("公司 Response VO")
@Data
@ToString(callSuper = true)
public class CompanyRespVO{

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

    @ApiModelProperty(value = "编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
