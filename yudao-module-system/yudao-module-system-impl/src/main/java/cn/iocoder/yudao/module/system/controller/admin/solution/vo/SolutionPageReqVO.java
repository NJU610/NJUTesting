package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 测试方案分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SolutionPageReqVO extends PageParam {

    @ApiModelProperty(value = "软件测试方案ID")
    private String table6Id;

    @ApiModelProperty(value = "质量部审核人id")
    private Long auditorId;

    @ApiModelProperty(value = "测试方案评审表ID")
    private String table13Id;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间", example = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

}
