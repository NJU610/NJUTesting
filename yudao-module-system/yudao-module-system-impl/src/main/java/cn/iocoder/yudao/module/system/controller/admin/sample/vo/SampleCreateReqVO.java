package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("样品创建 Request VO")
@Data
public class SampleCreateReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

//    @ApiModelProperty(value = "样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息", example = "线上", required = true)
//    @NotNull(message = "样品上传方式不能为空")
//    private String type;
//
//    @ApiModelProperty(value = "处理方式", example = "销毁/寄回", required = true)
//    @NotNull(message = "处理方式不能为空")
//    private String processType;
//
//    @ApiModelProperty(value = "样品信息", required = true)
//    @NotNull(message = "样品信息不能为空")
//    private String information;
//
//    @ApiModelProperty(value = "如果样品为线上上传，需要填写样品的url")
//    private String url;

}
