package cn.iocoder.yudao.module.system.controller.admin.sample.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 样品 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class SampleBaseVO {

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

    @ApiModelProperty(value = "审核意见")
    private String remark;

    @ApiModelProperty(value = "修改意见")
    private String modifyRemark;

    @ApiModelProperty(value = "样品状态 0.未发送 1.已发送 2.已审核 3.待修改 4.已修改 5.已处理 ")
    private Integer state;

}
