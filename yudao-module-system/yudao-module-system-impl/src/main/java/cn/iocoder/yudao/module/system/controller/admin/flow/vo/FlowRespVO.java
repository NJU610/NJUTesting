package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("项目流程 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlowRespVO extends FlowBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
