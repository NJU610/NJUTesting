package cn.iocoder.yudao.module.system.dal.dataobject.delegation;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 委托 DO
 *
 * @author lyw
 */
@TableName("system_delegation")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegationDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 委托名称
     */
    private String name;
    /**
     * 受理人编号
     */
    private Long acceptorId;
    /**
     * 发起时间
     */
    private Date launchTime;
    /**
     * 受理时间
     */
    private Date acceptTime;
    /**
     * 处理时间
     */
    private Date processTime;
    /**
     * 软件项目委托测试申请表ID
     */
    private String table2Id;
    /**
     * 委托测试软件功能列表ID
     */
    private String table3Id;
    /**
     * 文档材料url
     */
    private String url;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发起者编号
     */
    private Long creatorId;

}
