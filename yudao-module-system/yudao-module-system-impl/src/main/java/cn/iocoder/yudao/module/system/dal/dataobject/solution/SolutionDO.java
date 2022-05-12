package cn.iocoder.yudao.module.system.dal.dataobject.solution;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 测试方案 DO
 *
 * @author lyw
 */
@TableName("system_solution")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDO extends BaseDO {

    /**
     * 测试方案编号
     */
    @TableId
    private Long id;
    /**
     * 软件测试方案ID
     */
    private String table6Id;
    /**
     * 质量部审核人id
     */
    private Long auditorId;
    /**
     * 测试方案评审表ID
     */
    private String table13Id;

}
