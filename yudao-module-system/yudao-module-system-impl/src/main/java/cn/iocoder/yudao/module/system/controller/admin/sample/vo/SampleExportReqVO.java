package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "样品 Excel 导出 Request VO", description = "参数和 SamplePageReqVO 是一致的")
@Data
public class SampleExportReqVO {

    @ApiModelProperty(value = "样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息")
    private String type;

    @ApiModelProperty(value = "处理方式")
    private String processType;

    @ApiModelProperty(value = "如果样品为线上上传，需要填写样品的url")
    private String url;

    @ApiModelProperty(value = "样品信息")
    private String information;

    @ApiModelProperty(value = "审核人id，只能为选定的市场部或者测试部两个人中的一个")
    private Long verifyId;

    @ApiModelProperty(value = "样品状态 0.未发送 1.已发送 2.已审核 3.待修改 4.已修改 5.已处理 ")
    private Integer state;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间", example = "2019-01-01 00:00:00")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间", example = "2019-01-01 00:00:00")
    private Date endCreateTime;

}
