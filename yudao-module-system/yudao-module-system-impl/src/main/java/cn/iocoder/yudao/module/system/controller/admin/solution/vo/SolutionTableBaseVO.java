package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class SolutionTableBaseVO {

    @ApiModelProperty(value = "测试方案编号", example = "1", required = true)
    @NotNull(message = "测试方案编号不能为空")
    private Long solutionId;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

}
