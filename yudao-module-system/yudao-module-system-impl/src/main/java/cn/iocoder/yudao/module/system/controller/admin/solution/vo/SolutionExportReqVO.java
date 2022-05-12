package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 测试方案 Excel 导出 Request VO", description = "参数和 SolutionPageReqVO 是一致的")
@Data
public class SolutionExportReqVO {

    @ApiModelProperty(value = "软件测试方案ID")
    private String table6Id;

    @ApiModelProperty(value = "质量部审核人id")
    private Long auditorId;

    @ApiModelProperty(value = "测试方案评审表ID")
    private String table13Id;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
