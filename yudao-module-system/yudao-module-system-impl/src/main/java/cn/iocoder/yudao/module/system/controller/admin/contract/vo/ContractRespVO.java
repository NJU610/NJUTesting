package cn.iocoder.yudao.module.system.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("合同 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractRespVO extends ContractBaseVO {

    @ApiModelProperty(value = "合同编号", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
