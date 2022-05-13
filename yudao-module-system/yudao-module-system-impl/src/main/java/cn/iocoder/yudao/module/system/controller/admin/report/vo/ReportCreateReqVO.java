package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("测试报告创建 Request VO")
@Data
public class ReportCreateReqVO {

    @ApiModelProperty(value = "委托编号", required = true, example = "1")
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

}
