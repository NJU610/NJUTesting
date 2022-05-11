package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("样品提交 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SampleSubmitReqVO extends SampleEditBaseVO {
}
