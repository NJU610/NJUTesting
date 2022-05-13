package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 *  Excel VO
 *
 * @author 芋道源码
 */
@Data
public class FlowLogExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("委托的编号")
    private Long delegationId;

    @ExcelProperty("原状态")
    private Integer fromState;

    @ExcelProperty("现状态")
    private Integer toState;

    @ExcelProperty("操作人编号")
    private Long operatorId;

    @ExcelProperty("")
    private String remark;

    @ExcelProperty("日志时间")
    private Date operateTime;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("日志变量")
    private String mapValue;

}
