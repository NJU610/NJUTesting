package cn.iocoder.yudao.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 流程操作 DO
 *
 * @author qjy
 */
@TableName("system_operation")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDO extends BaseDO {

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
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作信息
     */
    private String remark;

}
