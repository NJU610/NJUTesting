package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@ApiModel("委托流程 Response VO")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class FlowLogInstanceResponseVO extends FlowLogBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Long id;

    @ApiModelProperty(value = "日志变量", required = true)
    private Map<String, Object> mapValue;
}
