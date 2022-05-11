package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("样品审核 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SampleAuditReqVO extends SampleEditBaseVO {

    @ApiModelProperty(value = "审核意见", example = "remark", required = true)
    @NotNull(message = "审核意见不能为空")
    private String remark;

}
