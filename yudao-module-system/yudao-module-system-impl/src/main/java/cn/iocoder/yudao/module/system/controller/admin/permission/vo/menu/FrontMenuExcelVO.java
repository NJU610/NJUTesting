package cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 前台菜单 Excel VO
 *
 * @author qjy
 */
@Data
public class FrontMenuExcelVO {

    @ExcelProperty("编号")
    private Long id;

}
