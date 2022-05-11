package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 样品 Excel VO
 *
 * @author lyw
 */
@Data
public class SampleExcelVO {

    @ExcelProperty("样品编号")
    private Long id;

    @ExcelProperty("样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息")
    private String type;

    @ExcelProperty("处理方式")
    private String processType;

    @ExcelProperty("如果样品为线上上传，需要填写样品的url")
    private String url;

    @ExcelProperty("样品信息")
    private String information;

    @ExcelProperty("审核人id，只能为选定的市场部或者测试部两个人中的一个")
    private Long verifyId;

    @ExcelProperty("审核意见")
    private String remark;

    @ExcelProperty("修改意见")
    private String modifyRemark;

    @ExcelProperty("样品状态 0.未发送 1.已发送 2.已审核 3.待修改 4.已修改 5.已处理 ")
    private Integer state;

    @ExcelProperty("创建时间")
    private Date createTime;

}
