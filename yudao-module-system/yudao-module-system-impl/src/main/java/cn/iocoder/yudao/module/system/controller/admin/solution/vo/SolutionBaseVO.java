package cn.iocoder.yudao.module.system.controller.admin.solution.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 测试方案 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class SolutionBaseVO {

    @ApiModelProperty(value = "软件测试方案ID")
    private String table6Id;

    @ApiModelProperty(value = "质量部审核人id")
    private Long auditorId;

    @ApiModelProperty(value = "测试方案评审表ID")
    private String table13Id;

}
