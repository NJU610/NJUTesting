package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "委托 Excel 导出 Request VO", description = "参数和 DelegationPageReqVO 是一致的")
@Data
public class DelegationExportReqVO {

    @ApiModelProperty(value = "发起者编号")
    private Long creatorId;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始发起时间")
    private Date beginLaunchTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束发起时间")
    private Date endLaunchTime;

    @ApiModelProperty(value = "委托名称")
    private String name;

    @ApiModelProperty(value = "软件项目委托测试申请表ID")
    private String table2Id;

    @ApiModelProperty(value = "委托测试软件功能列表ID")
    private String table3Id;

    @ApiModelProperty(value = "分配的市场部人员id")
    private Long marketDeptStaffId;

    @ApiModelProperty(value = "分配的测试部人员id")
    private Long testingDeptStaffId;

    @ApiModelProperty(value = " 软件文档评审表ID")
    private String table14Id;

    @ApiModelProperty(value = "报价单ID")
    private String offerId;

    @ApiModelProperty(value = "合同id")
    private Long contractId;

    @ApiModelProperty(value = "样品id")
    private Long sampleId;

    @ApiModelProperty(value = "测试方案id")
    private Long solutionId;

    @ApiModelProperty(value = "测试报告id")
    private Long reportId;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
