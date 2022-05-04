package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 项目流程 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FlowBaseVO {

    @ApiModelProperty(value = "合同编号", example = "1")
    private Long contractId;

    @ApiModelProperty(value = "样品编号", example = "1")
    private Long sampleId;

    @ApiModelProperty(value = "方案编号", example = "1")
    private Long solutionId;

    @ApiModelProperty(value = "报告编号", example = "1")
    private Long reportId;

    @ApiModelProperty(value = "当前状态", example = "10")
    private Integer state;

}
