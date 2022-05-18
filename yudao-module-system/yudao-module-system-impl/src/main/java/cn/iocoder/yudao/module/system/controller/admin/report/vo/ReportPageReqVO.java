package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 测试报告分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportPageReqVO extends PageParam {

    @ApiModelProperty(value = "软件测试报告ID")
    private String table7Id;

    @ApiModelProperty(value = "测试用例（电子记录）ID")
    private String table8Id;

    @ApiModelProperty(value = "软件测试记录（电子记录）ID")
    private String table9Id;

    @ApiModelProperty(value = "软件测试问题清单（电子记录）ID")
    private String table11Id;

    @ApiModelProperty(value = "测试部主管id")
    private Long testingDeptManagerId;

    @ApiModelProperty(value = "测试报告检查表ID")
    private String table10Id;

    @ApiModelProperty(value = "签字人id")
    private Long signatoryId;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间", example = "2019-01-01 00:00:00")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间", example = "2019-01-01 00:00:00")
    private Date endCreateTime;

}
