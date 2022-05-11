package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("样品更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SampleUpdateReqVO extends SampleEditBaseVO {

    @ApiModelProperty(value = "样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息")
    private String type;

    @ApiModelProperty(value = "处理方式")
    private String processType;

    @ApiModelProperty(value = "如果样品为线上上传，需要填写样品的url")
    private String url;

    @ApiModelProperty(value = "样品信息")
    private String information;

}
