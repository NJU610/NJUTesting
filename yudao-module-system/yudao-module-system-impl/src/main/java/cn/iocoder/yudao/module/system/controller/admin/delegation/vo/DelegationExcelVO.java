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

    @ExcelProperty("发起者编号")
    private Long creatorId;

    @ExcelProperty("发起时间")
    private Date launchTime;

    @ExcelProperty("委托名称")
    private String name;

    @ExcelProperty("软件项目委托测试申请表ID")
    private String table2Id;

    @ExcelProperty("委托测试软件功能列表ID")
    private String table3Id;

    @ExcelProperty("文档材料url")
    private String url;

    @ExcelProperty("分配的市场部人员id")
    private Long marketDeptStaffId;

    @ExcelProperty("分配的测试部人员id")
    private Long testingDeptStaffId;

    @ExcelProperty("市场部人员处理意见")
    private String marketRemark;

    @ExcelProperty("测试部人员处理意见")
    private String testingRemark;

    @ExcelProperty(" 软件文档评审表ID")
    private String table14Id;

    @ExcelProperty("报价单ID")
    private String offerId;

    @ExcelProperty("用户报价单意见")
    private String offerRemark;

    @ExcelProperty("合同id")
    private Long contractId;

    @ExcelProperty("样品id")
    private Long sampleId;

    @ExcelProperty("测试方案id")
    private Long solutionId;

    @ExcelProperty("测试报告id")
    private Long reportId;

    @ExcelProperty("状态")
    private Integer state;

    @ExcelProperty("创建时间")
    private Date createTime;

}
