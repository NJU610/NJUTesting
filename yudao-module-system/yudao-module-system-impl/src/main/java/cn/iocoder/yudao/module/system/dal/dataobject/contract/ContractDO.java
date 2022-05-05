package cn.iocoder.yudao.module.system.dal.dataobject.contract;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合同 DO
 *
 * @author lyw
 */
@TableName("system_contract")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDO extends BaseDO {

    /**
     * 合同编号
     */
    @TableId
    private Long id;
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
     * 软件委托测试合同ID
     */
    private String table4Id;
    /**
     * 软件项目委托测试保密协议ID
     */
    private String table5Id;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发起者编号
     */
    private Long creatorId;

}
