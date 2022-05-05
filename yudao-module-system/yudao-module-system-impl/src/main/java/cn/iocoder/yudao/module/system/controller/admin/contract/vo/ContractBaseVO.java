package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 合同 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ContractBaseVO {

    @ApiModelProperty(value = "受理人编号", example = "1")
    private Long acceptorId;

    @ApiModelProperty(value = "发起时间", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date launchTime;

    @ApiModelProperty(value = "受理时间", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date acceptTime;

    @ApiModelProperty(value = "处理时间", example = "2022-02-02 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date processTime;

    @ApiModelProperty(value = "软件委托测试合同ID", example = "abc")
    private String table4Id;

    @ApiModelProperty(value = "软件项目委托测试保密协议ID", example = "abc")
    private String table5Id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "发起者编号", example = "1")
    private Long creatorId;

}
