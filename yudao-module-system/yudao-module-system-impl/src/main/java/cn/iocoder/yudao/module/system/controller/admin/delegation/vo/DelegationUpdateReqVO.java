package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("委托更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationUpdateReqVO extends DelegationEditBaseVO {

    @ApiModelProperty(value = "委托名称", example = "example name")
    private String name;

    @ApiModelProperty(value = "文档材料url", example = "doc.zip")
    private String url;

    @ApiModelProperty(value = "备注", example = "remark")
    private String remark;

}
