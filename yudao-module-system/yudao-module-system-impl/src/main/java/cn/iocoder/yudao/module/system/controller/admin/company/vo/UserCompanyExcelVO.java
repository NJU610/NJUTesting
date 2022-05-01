package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 用户公司关联 Excel VO
 *
 * @author qjy
 */
@Data
public class UserCompanyExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("公司编号")
    private Long companyId;

    @ExcelProperty("创建时间")
    private Date createTime;

}
