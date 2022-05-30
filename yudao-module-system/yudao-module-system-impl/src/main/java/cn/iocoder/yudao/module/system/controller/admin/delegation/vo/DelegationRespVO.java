package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import javax.validation.constraints.NotNull;

@ApiModel("委托 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationRespVO extends DelegationBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Long id;

    @ApiModelProperty(value = "委托单位")
    private String clientUnit;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "软件名")
    private String softwareName;

}
