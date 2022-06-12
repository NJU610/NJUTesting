package cn.iocoder.yudao.module.system.service.job;

import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.HashMap;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Component
public class ReceiveReportJob extends QuartzJobBean {

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private FlowLogService flowLogService;

    @Resource
    private AdminUserService userService;

    private final Scheduler scheduler;

    @Autowired
    public ReceiveReportJob(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void executeInternal(@Nonnull JobExecutionContext context) throws JobExecutionException {
        Trigger trigger = context.getTrigger();
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        // 获取数据
        Long delegationId = dataMap.getLong("delegation_id");
        DelegationDO delegation = delegationMapper
                .validateDelegationState(delegationId,
                        DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUTO_CONFIRM_RECEIVE_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        Long creatorId = delegation.getCreatorId();
        flowLogService.saveLog(delegationId, creatorId,
                DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT,
                DelegationStateEnum.CLIENT_AUTO_CONFIRM_RECEIVE_TEST_REPORT,
                "客户：" + userService.getUser(creatorId).getNickname() + "未确认接收测试报告，到期自动确认",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
        // 删除任务
        try {
            // 暂停触发器的计时
            scheduler.pauseTrigger(trigger.getKey());
            // 移除触发器中的任务
            scheduler.unscheduleJob(trigger.getKey());
            // 删除任务
            scheduler.deleteJob(jobDetail.getKey());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
