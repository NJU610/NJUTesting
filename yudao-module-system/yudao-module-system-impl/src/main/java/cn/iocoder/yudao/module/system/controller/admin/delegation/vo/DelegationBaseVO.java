package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 委托 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DelegationBaseVO {

    @ApiModelProperty(value = "发起者编号")
    private Long creatorId;

    @ApiModelProperty(value = "发起时间", required = true)
    @NotNull(message = "发起时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date launchTime;

    @ApiModelProperty(value = "软件项目委托测试申请表ID")
    private String table2Id;

    @ApiModelProperty(value = "委托测试软件功能列表ID")
    private String table3Id;

    @ApiModelProperty(value = "文档材料url")
    private String url;

    @ApiModelProperty(value = "分配的市场部人员id")
    private Long marketDeptStaffId;

    @ApiModelProperty(value = "分配的测试部人员id")
    private Long testingDeptStaffId;

    @ApiModelProperty(value = "市场部人员处理意见")
    private String marketRemark;

    @ApiModelProperty(value = "测试部人员处理意见")
    private String testingRemark;

    @ApiModelProperty(value = " 软件文档评审表ID")
    private String table14Id;

    @ApiModelProperty(value = "报价单ID")
    private String offerId;

    @ApiModelProperty(value = "用户报价单意见")
    private String offerRemark;

    @ApiModelProperty(value = "合同id")
    private Long contractId;

    @ApiModelProperty(value = "样品id")
    private Long sampleId;

    @ApiModelProperty(value = "测试方案id")
    private Long solutionId;

    @ApiModelProperty(value = "测试报告id")
    private Long reportId;

}
