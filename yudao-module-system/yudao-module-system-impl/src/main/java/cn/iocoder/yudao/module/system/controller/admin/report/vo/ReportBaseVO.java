package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 测试报告 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ReportBaseVO {

    @ApiModelProperty(value = "软件测试报告ID")
    private String table7Id;

    @ApiModelProperty(value = "测试用例（电子记录）ID")
    private String table8Id;

    @ApiModelProperty(value = "软件测试记录（电子记录）ID")
    private String table9Id;

    @ApiModelProperty(value = "软件测试问题清单（电子记录）ID")
    private String table11Id;

    @ApiModelProperty(value = "测试部主管id")
    private Long testingDeptManagerId;

    @ApiModelProperty(value = "测试部主管审核意见")
    private String managerRemark;

    @ApiModelProperty(value = "测试报告检查表ID")
    private String table10Id;

    @ApiModelProperty(value = "签字人审核意见")
    private String signatoryRemark;

    @ApiModelProperty(value = "签字人id")
    private Long signatoryId;

    @ApiModelProperty(value = "客户意见")
    private String clientRemark;

}
