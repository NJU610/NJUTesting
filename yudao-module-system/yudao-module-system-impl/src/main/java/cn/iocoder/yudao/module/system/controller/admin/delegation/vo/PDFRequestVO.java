package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("通过表格编号生成PDF Request VO")
@Data
@EqualsAndHashCode
@ToString
public class PDFRequestVO {

    @ApiModelProperty(value = "表格在MongoDB中的主键编号", example = "627cb1baffb4134949aa10c8", required = true)
    @NotNull(message = "表格编号不能为空")
    String tableId;

    @ApiModelProperty(value = "table的名称，例如一般表格为填写table10，标价单为offer", example = "table10", required = true)
    @NotNull(message = "table的名称不能为空")
    String tableName;

}
