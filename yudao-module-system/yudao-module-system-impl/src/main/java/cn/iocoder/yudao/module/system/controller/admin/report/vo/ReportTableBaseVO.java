package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ReportTableBaseVO {

    @ApiModelProperty(value = "报告编号", example = "1", required = true)
    @NotNull(message = "报告编号不能为空")
    private Long reportId;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

}
