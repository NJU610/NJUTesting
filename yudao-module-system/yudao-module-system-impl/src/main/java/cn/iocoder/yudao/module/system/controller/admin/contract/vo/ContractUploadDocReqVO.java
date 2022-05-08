package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("合同更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractUploadDocReqVO extends ContractEditBaseVO {

    @ApiModelProperty(value = "实体合同材料url", example = "example.zip", required = true)
    @NotNull(message = "实体合同材料url不能为空")
    private String url;

}
