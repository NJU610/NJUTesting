package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("合同相关表格保存 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractSaveTableReqVO extends ContractTableBaseVO {
}
