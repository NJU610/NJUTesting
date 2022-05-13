package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 测试方案创建 Request VO")
@Data
public class SolutionCreateReqVO{
    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;
}
