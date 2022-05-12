package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 *  Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FlowLogBaseVO {

    @ApiModelProperty(value = "委托的编号", required = true)
    @NotNull(message = "委托的编号不能为空")
    private Long delegationId;

    @ApiModelProperty(value = "原状态", required = true)
    private Integer fromState;

    @ApiModelProperty(value = "现状态", required = true)
    @NotNull(message = "现状态不能为空")
    private Integer toState;

    @ApiModelProperty(value = "操作人编号", required = true)
    @NotNull(message = "操作人编号不能为空")
    private Long operatorId;

    @ApiModelProperty(value = "日志内容", required = true)
    @NotNull(message = "不能为空")
    private String remark;

    @ApiModelProperty(value = "日志时间", required = true)
    @NotNull(message = "日志时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date operateTime;

}
