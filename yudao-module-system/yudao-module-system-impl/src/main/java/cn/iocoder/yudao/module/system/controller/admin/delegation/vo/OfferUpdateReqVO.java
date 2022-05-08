package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;

@ApiModel("报价单更新 Request VO")
@Data
@EqualsAndHashCode
@ToString
public class OfferUpdateReqVO {

    @ApiModelProperty(value = "报价单编号", example = "abc", required = true)
    @NotNull(message = "报价单编号不能为空")
    private String id;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

}
