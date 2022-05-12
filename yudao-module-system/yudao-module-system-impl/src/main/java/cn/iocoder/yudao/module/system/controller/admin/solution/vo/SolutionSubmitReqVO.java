package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("测试方案提交 Request VO")
@Data
public class SolutionSubmitReqVO {

    @ApiModelProperty(value = "测试方案编号", example = "1", required = true)
    @NotNull(message = "测试方案编号不能为空")
    private Long solutionId;

}
