package cn.iocoder.yudao.module.bpm.service.delegation;

import cn.iocoder.yudao.framework.common.util.number.NumberUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAssignReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAuditReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationSubmitReqVO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import cn.iocoder.yudao.module.bpm.service.definition.BpmProcessDefinitionService;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.*;

@Service
@Validated
@Slf4j
public class BpmDelegationServiceImpl implements BpmDelegationService {

    @Resource
    private BpmProcessDefinitionService processDefinitionService;

    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createDelegation(BpmDelegationCreateReqVO createReqVO) {
        // ??????????????????
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(createReqVO.getProcessDefinitionId());
        // ????????????
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", createReqVO.getName());
        variables.put("owner", getLoginUserId());
        return createProcessInstance(definition, variables, null);
    }

    @Override
    public void submitDelegation(BpmDelegationSubmitReqVO submitVo) {
        // ??????????????????
        Long loginUserId = getLoginUserId();
        Task task = checkTask(loginUserId, submitVo.getId());
        // ????????????????????????
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }

        // ???????????????????????????
        taskService.complete(task.getId());
    }

    @Override
    public void assignDelegation(BpmDelegationAssignReqVO assignReqVO, String department) {
        // ??????????????????
        Long loginUserId = getLoginUserId();
        Task task = checkTask(loginUserId, assignReqVO.getId());
        // ????????????????????????
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }
        // ???????????????id
        Long userId = assignReqVO.getUserId();
        Map<String, Object> variables = instance.getProcessVariables();
        if (Objects.equals(department, "marketing")) {
            variables.put("marketDeptStaffId", userId);
        } else if (Objects.equals(department, "testing")) {
            variables.put("testingDeptStaffId", userId);
        }

        // ????????????
        taskService.complete(task.getId(), variables);
    }

    @Override
    public void auditDelegation(BpmDelegationAuditReqVO auditReqVO) {
        // ??????????????????
        Long loginUserId = getLoginUserId();
        Task task = checkTask(loginUserId, auditReqVO.getId());
        // ????????????????????????
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }
        // ??????????????????
        Boolean result = auditReqVO.getResult();
        String remark = auditReqVO.getRemark();
        Map<String, Object> variables = instance.getProcessVariables();
        variables.put("result", result);
        variables.put("remark", remark);

        // ????????????
        taskService.complete(task.getId(), variables);
    }

    private String createProcessInstance(ProcessDefinition definition,
                                          Map<String, Object> variables, String businessKey) {
        // ??????????????????
        if (definition == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }
        if (definition.isSuspended()) {
            throw exception(PROCESS_DEFINITION_IS_SUSPENDED);
        }

        // ??????????????????
        ProcessInstance instance = runtimeService.startProcessInstanceById(definition.getId(), businessKey, variables);
        // ??????????????????
        runtimeService.setProcessInstanceName(instance.getId(), definition.getName());

        return instance.getId();
    }

    private Task checkTask(Long userId, String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw exception(TASK_COMPLETE_FAIL_NOT_EXISTS);
        }
        if (!Objects.equals(userId, NumberUtils.parseLong(task.getAssignee()))) {
            throw exception(TASK_COMPLETE_FAIL_ASSIGN_NOT_SELF);
        }
        return task;
    }

}
