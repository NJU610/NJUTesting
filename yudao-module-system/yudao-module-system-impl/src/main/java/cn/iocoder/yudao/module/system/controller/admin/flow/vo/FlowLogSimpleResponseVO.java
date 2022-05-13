package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("委托流程简略信息Response VO")
@Data
@ToString(callSuper = true)
public class FlowLogSimpleResponseVO {

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private Long id;

    @ApiModelProperty(value = "日志内容", required = true)
    @NotNull(message = "不能为空")
    private String remark;

    @ApiModelProperty(value = "日志时间", required = true)
    @NotNull(message = "日志时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date operateTime;
}
