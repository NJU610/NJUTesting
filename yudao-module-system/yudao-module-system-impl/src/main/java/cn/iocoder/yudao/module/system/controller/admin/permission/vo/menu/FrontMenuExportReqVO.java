package cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "前台菜单 Excel 导出 Request VO", description = "参数和 FrontMenuPageReqVO 是一致的")
@Data
public class FrontMenuExportReqVO {

    @ApiModelProperty(value = "编号")
    private Long id;

}
