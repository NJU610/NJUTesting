package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SampleEditBaseVO {

    @ApiModelProperty(value = "样品编号", required = true)
    @NotNull(message = "样品编号不能为空")
    private Long id;

}
