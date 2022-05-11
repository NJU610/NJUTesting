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
     * 编号
     */
    private Long delegationId;
    /**
     * 操作人编号
     */
    private Long operatorId;
    /**
     * 原状态
     */
    private Integer fromState;
    /**
     * 目标状态
     */
    private Integer toState;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作信息
     */
    private String remark;
    /**
     *  操作变量
     */
    private String mapValue;

}
