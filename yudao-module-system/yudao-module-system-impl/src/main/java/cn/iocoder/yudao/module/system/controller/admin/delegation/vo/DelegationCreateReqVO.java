package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 委托创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationCreateReqVO extends DelegationBaseVO {

    @ApiModelProperty(value = "发起时间", required = true)
    @NotNull(message = "发起时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date launchTime;

}
