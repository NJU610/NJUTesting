package cn.iocoder.yudao.module.system.dal.dataobject.delegation;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 委托-软件项目委托测试工作检查表 DO
 *
 * @author lyw
 */
@TableName("system_delegation_table12")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegationTable12DO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 委托编号
     */
    private Long delegationId;
    /**
     * 软件项目委托测试工作检查表编号
     */
    private String table12Id;

}
