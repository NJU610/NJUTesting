package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ContractTableBaseVO {

    @ApiModelProperty(value = "合同编号", example = "1", required = true)
    @NotNull(message = "合同编号不能为空")
    private Long contractId;

    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

}
