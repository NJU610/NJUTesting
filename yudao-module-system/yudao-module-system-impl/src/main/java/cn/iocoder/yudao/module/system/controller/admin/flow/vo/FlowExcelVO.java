package cn.iocoder.yudao.module.system.controller.admin.flow.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 项目流程 Excel VO
 *
 * @author lyw
 */
@Data
public class FlowExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("创建时间")
    private Date launchTime;

    @ExcelProperty("创建人编号")
    private Long creatorId;

    @ExcelProperty("委托编号")
    private Long delegationId;

    @ExcelProperty("合同编号")
    private Long contractId;

    @ExcelProperty("样品编号")
    private Long sampleId;

    @ExcelProperty("方案编号")
    private Long solutionId;

    @ExcelProperty("报告编号")
    private Long reportId;

    @ExcelProperty("当前状态")
    private Integer state;

    @ExcelProperty("创建时间")
    private Date createTime;

}
