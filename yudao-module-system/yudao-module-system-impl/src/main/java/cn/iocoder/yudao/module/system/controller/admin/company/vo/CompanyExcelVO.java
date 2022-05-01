package cn.iocoder.yudao.module.system.controller.admin.company.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 公司 Excel VO
 *
 * @author qjy
 */
@Data
public class CompanyExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("公司名称")
    private String name;

    @ExcelProperty("公司地址")
    private String address;

    @ExcelProperty("公司联系方式")
    private String phone;

    @ExcelProperty("公司编码，认证公司时使用")
    private String code;

    @ExcelProperty("创建时间")
    private Date createTime;

}
