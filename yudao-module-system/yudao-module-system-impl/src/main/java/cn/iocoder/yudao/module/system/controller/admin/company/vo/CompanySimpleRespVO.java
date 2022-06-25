package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("公司精简信息 Response VO")
@Data
@ToString(callSuper = true)
public class CompanySimpleRespVO {

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
