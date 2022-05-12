package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 测试方案 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SolutionRespVO extends SolutionBaseVO {

    @ApiModelProperty(value = "测试方案编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
