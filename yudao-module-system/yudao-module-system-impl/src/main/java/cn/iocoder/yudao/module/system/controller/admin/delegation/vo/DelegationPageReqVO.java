package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("委托分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationPageReqVO extends PageParam {

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

    @ApiModelProperty(value = "软件项目委托测试工作检查表ID")
    private String table12Id;

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

    @ApiModelProperty(value = "状态列表")
    private List<Integer> state;

    @ApiModelProperty(value = "取消原因")
    private String cancelRemark;

    @ApiModelProperty(value = "排序字段", example = "creator_id")
    private String orderField;

    @ApiModelProperty(value = "是否升序, true 升序, false 降序")
    private Boolean asc;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
