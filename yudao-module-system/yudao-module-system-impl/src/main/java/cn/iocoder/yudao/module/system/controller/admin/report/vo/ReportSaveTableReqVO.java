package cn.iocoder.yudao.module.system.controller.admin.report.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("测试报告相关表格保存 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportSaveTableReqVO extends ReportTableBaseVO {
}
