package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "合同 Excel 导出 Request VO", description = "参数和 ContractPageReqVO 是一致的")
@Data
public class ContractExportReqVO {

    @ApiModelProperty(value = "软件委托测试合同ID")
    private String table4Id;

    @ApiModelProperty(value = "软件项目委托测试保密协议ID")
    private String table5Id;

    @ApiModelProperty(value = "实体合同材料url")
    private String url;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
