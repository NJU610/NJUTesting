package cn.iocoder.yudao.module.system.dal.dataobject.flow;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程操作 DO
 *
 * @author qjy
 */
@TableName("system_flow_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowLogDO extends BaseDO {

    /**
     * 操作编号
     */
    @TableId
    private Long id;
    /**
     * 流程编号
     */
    private Long flowId;
    /**
     * 操作人编号
     */
    private Long operatorId;
    /**
     * 原状态
     */
    private int fromState;
    /**
     * 目标状态
     */
    private int toState;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作信息
     */
    private String remark;

}
