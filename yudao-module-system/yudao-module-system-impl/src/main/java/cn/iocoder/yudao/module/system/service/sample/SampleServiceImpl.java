package cn.iocoder.yudao.module.system.service.sample;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.enums.sample.SampleStateEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.sample.SampleConvert;
import cn.iocoder.yudao.module.system.dal.mysql.sample.SampleMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 样品 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class SampleServiceImpl implements SampleService {

    @Resource
    private SampleMapper sampleMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @Override
    public Long createSample(SampleCreateReqVO createReqVO) {
        // 校验委托存在
        Long delegationId = createReqVO.getDelegationId();
        delegationMapper.validateDelegationExists(delegationId);
        DelegationDO delegation = delegationMapper
                .validateDelegationState(delegationId, DelegationStateEnum.CLIENT_SENDING_SAMPLE);
        // 插入
        SampleDO sample = SampleConvert.INSTANCE.convert(createReqVO);
        sample.setState(SampleStateEnum.UNSENT.getState());
        sampleMapper.insert(sample);
        Long sampleId = sample.getId();
        // 更新委托
        delegation.setSampleId(sampleId);
        delegation.setState(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState());
        delegation.setState(DelegationStateEnum.CHECKING_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
        // 返回
        return sampleId;
    }

    @Override
    public void updateSample(SampleUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSampleExists(updateReqVO.getId());
        // 更新
        SampleDO updateObj = SampleConvert.INSTANCE.convert(updateReqVO);
        sampleMapper.updateById(updateObj);
    }

    @Override
    public void submitSample(SampleSubmitReqVO submitReqVO) {
        // 校验存在
        this.validateSampleExists(submitReqVO.getId());
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateBySample(submitReqVO.getId(),
                DelegationStateEnum.CLIENT_SENDING_SAMPLE,
                DelegationStateEnum.SAMPLE_CHECK_FAIL_RESENDING_SAMPLE,
                DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE_INFO);
        SampleDO sample = this.validateSampleState(submitReqVO.getId(), SampleStateEnum.UNSENT);
        // 更新状态
        sample.setState(SampleStateEnum.SENT.getState());
        sampleMapper.updateById(sample);
        delegation.setState(DelegationStateEnum.CHECKING_SAMPLE.getState());
        delegationMapper.updateById(delegation);
    }

    @Override
    public void auditSampleSuccess(SampleAuditReqVO auditReqVO) {
        // 审核
        DelegationDO delegation = this.auditSample(auditReqVO, true);
        // 更新委托
        delegation.setState(DelegationStateEnum.SAMPLE_CHECK_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void auditSampleFail1(SampleAuditReqVO auditReqVO) {
        // 审核
        DelegationDO delegation = this.auditSample(auditReqVO, false);
        // 更新委托
        delegation.setState(DelegationStateEnum.SAMPLE_CHECK_FAIL_RESENDING_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void auditSampleFail2(SampleAuditReqVO auditReqVO) {
        // 审核
        DelegationDO delegation = this.auditSample(auditReqVO, false);
        // 更新委托
        delegation.setState(DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE_INFO.getState());
        delegationMapper.updateById(delegation);
        // TODO 更新日志
    }

    @Override
    public void deleteSample(Long id) {
        // 校验存在
        this.validateSampleExists(id);
        // 删除
        sampleMapper.deleteById(id);
    }

    private void validateSampleExists(Long id) {
        if (sampleMapper.selectById(id) == null) {
            throw exception(SAMPLE_NOT_EXISTS);
        }
    }

    private SampleDO validateSampleState(Long id, SampleStateEnum state) {
        SampleDO sample = sampleMapper.selectById(id);
        if (!Objects.equals(sample.getState(), state.getState())) {
            throw exception(SAMPLE_STATE_ERROR);
        }
        return sample;
    }

    private DelegationDO auditSample(SampleAuditReqVO auditReqVO, Boolean success) {
        // 校验存在
        Long sampleId = auditReqVO.getId();
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        this.validateSampleExists(sampleId);
        this.validateSampleState(sampleId, SampleStateEnum.SENT);
        // 校验状态
        DelegationDO delegation = delegationMapper
                .validateDelegationStateBySample(sampleId, DelegationStateEnum.CHECKING_SAMPLE);
        if (!Objects.equals(delegation.getTestingDeptStaffId(), loginUserId) &&
                !Objects.equals(delegation.getMarketDeptStaffId(), loginUserId)) {
            throw exception(SAMPLE_AUDITOR_ERROR);
        }
        // 更新
        SampleDO updateObj = SampleConvert.INSTANCE.convert(auditReqVO);
        updateObj.setVerifyId(loginUserId);
        if (success) {
            updateObj.setState(SampleStateEnum.VERIFIED.getState());
        } else {
            updateObj.setState(SampleStateEnum.UNSENT.getState());
        }
        sampleMapper.updateById(updateObj);
        return delegation;
    }

    @Override
    public SampleDO getSample(Long id) {
        return sampleMapper.selectById(id);
    }

    @Override
    public List<SampleDO> getSampleList(Collection<Long> ids) {
        return sampleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SampleDO> getSamplePage(SamplePageReqVO pageReqVO) {
        return sampleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SampleDO> getSampleList(SampleExportReqVO exportReqVO) {
        return sampleMapper.selectList(exportReqVO);
    }

}
