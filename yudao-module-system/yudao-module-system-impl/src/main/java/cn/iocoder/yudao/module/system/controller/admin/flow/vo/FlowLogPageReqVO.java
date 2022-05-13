package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlowLogPageReqVO extends PageParam {

    @ApiModelProperty(value = "委托的编号")
    private Long delegationId;

    @ApiModelProperty(value = "原状态")
    private Integer fromState;

    @ApiModelProperty(value = "现状态")
    private Integer toState;

    @ApiModelProperty(value = "操作人编号")
    private Long operatorId;

    @ApiModelProperty(value = "")
    private String remark;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始日志时间")
    private Date beginOperateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束日志时间")
    private Date endOperateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

    @ApiModelProperty(value = "日志变量")
    private String mapValue;

}
