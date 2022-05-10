package cn.iocoder.yudao.module.bpm.service.delegation;

import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAssignReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationAuditReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationCreateReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.delegation.vo.BpmDelegationSubmitReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCreateReqVO;

import javax.validation.Valid;

public interface BpmDelegationService {

    /**
     * 创建流程实例（提供给前端）
     *
     * @param createReqVO 创建信息
     * @return 实例的编号
     */
    String createDelegation(@Valid BpmDelegationCreateReqVO createReqVO);

    /**
     * 提交流程实例（提供给前端）
     *
     * @param submitVo 提交信息
     */
    void submitDelegation(@Valid BpmDelegationSubmitReqVO submitVo);

    /**
     * 分配委托
     *
     * @param assignReqVO 分配信息
     */
    void assignDelegation(@Valid BpmDelegationAssignReqVO assignReqVO, String department);

    /**
     * 审核委托
     *
     * @param auditReqVO 审核信息
     */
    void auditDelegation(@Valid BpmDelegationAuditReqVO auditReqVO);


}
