package cn.iocoder.yudao.module.system.dal.dataobject.sample;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 样品 DO
 *
 * @author lyw
 */
@TableName("system_sample")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleDO extends BaseDO {

    /**
     * 样品编号
     */
    @TableId
    private Long id;
    /**
     * 样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息
     */
    private String type;
    /**
     * 处理方式
     */
    private String processType;
    /**
     * 如果样品为线上上传，需要填写样品的url
     */
    private String url;
    /**
     * 样品信息
     */
    private String information;
    /**
     * 审核人id，只能为选定的市场部或者测试部两个人中的一个
     */
    private Long verifyId;
    /**
     * 审核意见
     */
    private String remark;
    /**
     * 修改意见
     */
    private String modifyRemark;
    /**
     * 样品状态 0.未发送 1.已发送 2.已审核 3.待修改 4.已修改 5.已处理
     */
    private Integer state;

}
