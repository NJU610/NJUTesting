package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("样品 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SampleRespVO extends SampleBaseVO {

    @ApiModelProperty(value = "样品编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
