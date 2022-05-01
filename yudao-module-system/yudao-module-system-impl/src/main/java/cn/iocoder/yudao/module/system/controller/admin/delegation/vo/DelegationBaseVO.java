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

    @ApiModelProperty(value = "委托名称")
    private String name;

    @ApiModelProperty(value = "受理人编号")
    private Long acceptorId;

    @ApiModelProperty(value = "受理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date acceptTime;

    @ApiModelProperty(value = "处理时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date processTime;

    @ApiModelProperty(value = "软件项目委托测试申请表ID")
    private String table2Id;

    @ApiModelProperty(value = "委托测试软件功能列表ID")
    private String table3Id;

    @ApiModelProperty(value = "文档材料url")
    private String url;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "发起者编号")
    private Long creatorId;

}
