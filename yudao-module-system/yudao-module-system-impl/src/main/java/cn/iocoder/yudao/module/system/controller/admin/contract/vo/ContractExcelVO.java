package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合同 Excel VO
 *
 * @author lyw
 */
@Data
public class ContractExcelVO {

    @ExcelProperty("合同编号")
    private Long id;

    @ExcelProperty("受理人编号")
    private Long acceptorId;

    @ExcelProperty("发起时间")
    private Date launchTime;

    @ExcelProperty("受理时间")
    private Date acceptTime;

    @ExcelProperty("处理时间")
    private Date processTime;

    @ExcelProperty("软件委托测试合同ID")
    private String table4Id;

    @ExcelProperty("软件项目委托测试保密协议ID")
    private String table5Id;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("发起者编号")
    private Long creatorId;

}
