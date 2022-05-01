package cn.iocoder.yudao.module.system.dal.dataobject.flow;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目流程 DO
 *
 * @author lyw
 */
@TableName("system_flow")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 创建时间
     */
    private Date launchTime;
    /**
     * 创建人编号
     */
    private Long creatorId;
    /**
     * 委托编号
     */
    private Long delegationId;
    /**
     * 合同编号
     */
    private Long contractId;
    /**
     * 样品编号
     */
    private Long sampleId;
    /**
     * 方案编号
     */
    private Long solutionId;
    /**
     * 报告编号
     */
    private Long reportId;
    /**
     * 当前状态
     */
    private Integer state;

}
