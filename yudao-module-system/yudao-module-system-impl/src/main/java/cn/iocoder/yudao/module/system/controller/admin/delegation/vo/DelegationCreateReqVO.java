package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("委托创建 Request VO")
@Data
@ToString
public class DelegationCreateReqVO {

    @ApiModelProperty(value = "委托名称", example = "example name", required = true)
    @NotNull(message = "委托名称不能为空")
    private String name;

}
