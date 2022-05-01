package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class DelegationTableBaseVo {

    @ApiModelProperty(value = "表名", required = true)
    @NotNull(message = "表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "数据", required = true)
    private Map<String, Object> data;

}
