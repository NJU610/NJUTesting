package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("委托表格导出 Request VO")
@Data
public class DelegationExportTableReqVO {

    @ApiModelProperty(value = "委托编号", example = "1", required = true)
    @NotNull(message = "委托编号不能为空")
    private Long delegationId;

    @ApiModelProperty(value = "表名", example = "table2", required = true)
    @NotNull(message = "表名不能为空")
    private String tableName;

}
