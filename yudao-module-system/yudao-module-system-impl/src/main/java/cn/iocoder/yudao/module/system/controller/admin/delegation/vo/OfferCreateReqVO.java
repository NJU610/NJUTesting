package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;

@ApiModel("报价单创建 Request VO")
@Data
@ToString
public class OfferCreateReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

}
