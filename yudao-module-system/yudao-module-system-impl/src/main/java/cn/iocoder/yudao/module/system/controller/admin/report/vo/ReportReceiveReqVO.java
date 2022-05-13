package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("接收测试报告 Request VO")
@Data
@EqualsAndHashCode
@ToString
public class ReportReceiveReqVO {

    @ApiModelProperty(value = "测试报告编号", example = "1", required = true)
    @NotNull(message = "测试报告编号不能为空")
    private Long id;

}
