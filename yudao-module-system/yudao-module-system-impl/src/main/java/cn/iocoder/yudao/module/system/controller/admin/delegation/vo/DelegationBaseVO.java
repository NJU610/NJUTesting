package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 委托 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class DelegationBaseVO {

    @ApiModelProperty(value = "受理人编号", example = "1")
    private Long acceptorId;

    @ApiModelProperty(value = "发起时间, 格式为yy-mm-dd hh:mm:ss", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date launchTime;

    @ApiModelProperty(value = "受理时间, 格式为yy-mm-dd hh:mm:ss", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date acceptTime;

    @ApiModelProperty(value = "处理时间, 格式为yy-mm-dd hh:mm:ss", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date processTime;

    @ApiModelProperty(value = "软件项目委托测试申请表ID", example = "abc")
    private String table2Id;

    @ApiModelProperty(value = "委托测试软件功能列表ID", example = "abc")
    private String table3Id;

    @ApiModelProperty(value = "文档材料url", example = "doc.zip")
    private String url;

    @ApiModelProperty(value = "备注", example = "remark")
    private String remark;

    @ApiModelProperty(value = "发起者编号", example = "1")
    private Long creatorId;

}
