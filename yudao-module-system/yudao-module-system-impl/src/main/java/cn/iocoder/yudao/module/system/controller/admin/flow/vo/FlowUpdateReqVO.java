package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("项目流程更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlowUpdateReqVO extends FlowBaseVO {

    @ApiModelProperty(value = "流程编号", example = "1", required = true)
    @NotNull(message = "流程编号不能为空")
    private Long id;

}