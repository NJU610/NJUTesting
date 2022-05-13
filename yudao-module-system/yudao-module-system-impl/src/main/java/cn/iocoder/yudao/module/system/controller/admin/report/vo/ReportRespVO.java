package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 测试报告 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportRespVO extends ReportBaseVO {

    @ApiModelProperty(value = "测试报告编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
