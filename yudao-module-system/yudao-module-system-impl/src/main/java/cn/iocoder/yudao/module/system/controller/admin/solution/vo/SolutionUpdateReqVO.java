package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 测试方案更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SolutionUpdateReqVO extends SolutionBaseVO {

    @ApiModelProperty(value = "测试方案编号", required = true)
    @NotNull(message = "测试方案编号不能为空")
    private Long id;

}
