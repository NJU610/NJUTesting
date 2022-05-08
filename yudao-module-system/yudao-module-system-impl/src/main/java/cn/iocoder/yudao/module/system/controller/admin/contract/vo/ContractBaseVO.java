package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 合同 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ContractBaseVO {

    @ApiModelProperty(value = "软件委托测试合同ID")
    private String table4Id;

    @ApiModelProperty(value = "软件项目委托测试保密协议ID")
    private String table5Id;

    @ApiModelProperty(value = "客户审核合同意见")
    private String clientRemark;

    @ApiModelProperty(value = "市场部人员审核合同意见")
    private String staffRemark;

    @ApiModelProperty(value = "实体合同材料url")
    private String url;

}
