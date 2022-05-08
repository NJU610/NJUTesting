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
     * 发起者编号
     */
    private Long creatorId;
    /**
     * 发起时间
     */
    private Date launchTime;
    /**
     * 委托名称
     */
    private String name;
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
     * 分配的市场部人员id
     */
    private Long marketDeptStaffId;
    /**
     * 分配的测试部人员id
     */
    private Long testingDeptStaffId;
    /**
     * 市场部人员处理意见
     */
    private String marketRemark;
    /**
     * 测试部人员处理意见
     */
    private String testingRemark;
    /**
     *  软件文档评审表ID
     */
    private String table14Id;
    /**
     * 报价单ID
     */
    private String offerId;
    /**
     * 用户报价单意见
     */
    private String offerRemark;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 样品id
     */
    private Long sampleId;
    /**
     * 测试方案id
     */
    private Long solutionId;
    /**
     * 测试报告id
     */
    private Long reportId;
    /**
     * 状态
     */
    private Integer state;

}
