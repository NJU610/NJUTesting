package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("委托分配 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DelegationDistributeReqVO extends DelegationEditBaseVO {

    @ApiModelProperty(value = "接收委托的测试人员id", example = "1", required = true)
    @NotNull(message = "测试人员id不能为空")
    private Long acceptorId;

}
