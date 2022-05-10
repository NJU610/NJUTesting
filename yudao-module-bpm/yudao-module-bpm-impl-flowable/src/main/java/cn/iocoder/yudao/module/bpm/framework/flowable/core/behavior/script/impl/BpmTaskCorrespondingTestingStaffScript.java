package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior.script.impl;

import cn.iocoder.yudao.framework.common.util.collection.SetUtils;
import cn.iocoder.yudao.framework.common.util.number.NumberUtils;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 分配给指定测试部人员的 Script 实现类
 *
 * @author qjy
 */
@Component
public class BpmTaskCorrespondingTestingStaffScript extends BpmTaskAssignLeaderAbstractScript{

    @Resource
    @Lazy // 解决循环依赖
    private BpmProcessInstanceService bpmProcessInstanceService;

    @Override
    public Set<Long> calculateTaskCandidateUsers(TaskEntity task) {
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(task.getProcessInstanceId());
        Long staff = NumberUtils.parseLong(processInstance.getProcessVariables().get("testingDeptStaffId").toString());
        return SetUtils.asSet(staff);
    }

    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.CORRESPONDING_TESTING_STAFF;
    }
}
