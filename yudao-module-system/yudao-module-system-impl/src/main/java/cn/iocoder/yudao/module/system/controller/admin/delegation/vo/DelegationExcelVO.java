package cn.iocoder.yudao.module.system.controller.admin.delegation.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 委托 Excel VO
 *
 * @author lyw
 */
@Data
public class DelegationExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("委托名称")
    private String name;

    @ExcelProperty("受理人编号")
    private Long acceptorId;

    @ExcelProperty("发起时间")
    private Date launchTime;

    @ExcelProperty("受理时间")
    private Date acceptTime;

    @ExcelProperty("处理时间")
    private Date processTime;

    @ExcelProperty("软件项目委托测试申请表ID")
    private String table2Id;

    @ExcelProperty("委托测试软件功能列表ID")
    private String table3Id;

    @ExcelProperty("文档材料url")
    private String url;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("发起者编号")
    private Long creatorId;

}
