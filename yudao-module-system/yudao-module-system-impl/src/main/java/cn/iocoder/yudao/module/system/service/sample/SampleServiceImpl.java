package cn.iocoder.yudao.module.system.service.sample;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.enums.sample.SampleStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.springframework.context.annotation.Lazy;
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
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
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

    @Lazy
    @Resource
    private FlowLogService flowLogService;

    @Resource
    private AdminUserService userService;

    @Override
    public Long createSample(SampleCreateReqVO createReqVO) {
        // 校验委托存在
        Long delegationId = createReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper
                .validateDelegationState(delegationId, DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO);
        // 插入
        SampleDO sample = SampleConvert.INSTANCE.convert(createReqVO);
        sample.setState(SampleStateEnum.UNSENT.getState());
        sampleMapper.insert(sample);
        Long sampleId = sample.getId();
        // 更新委托
        delegation.setSampleId(sampleId);
        delegationMapper.updateById(delegation);
        // 更新日志
//        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
//                DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO, DelegationStateEnum.CHECKING_SAMPLE,
//                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 上传了样品，测试部/市场部验收样品中",
//                new HashMap<String, Object>(){{put("delegation", delegation);put("sample", sample);}});
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
        // 校验存在和状态
        SampleDO sample = this.validateSampleState(submitReqVO.getId(), SampleStateEnum.UNSENT);
        DelegationDO delegation = delegationMapper.validateDelegationStateBySample(submitReqVO.getId(),
                DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO, DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE);
        DelegationStateEnum oldState = DelegationStateEnum.getByState(delegation.getState());
        // 更新状态
        sample.setState(SampleStateEnum.SENT.getState());
        sampleMapper.updateById(sample);
        delegation.setState(DelegationStateEnum.CHECKING_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                oldState, DelegationStateEnum.CHECKING_SAMPLE,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " " +
                        (Objects.equals(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO, oldState) ? "" : "重新") +
                        "上传了样品，测试部/市场部验收样品中",
                new HashMap<String, Object>(){{put("delegation", delegation);put("sample", sample);}});
    }

    @Override
    public void auditSampleSuccess(SampleAuditReqVO auditReqVO) {
        // 审核
        DelegationDO delegation = this.auditSample(auditReqVO, true);
        SampleDO sample = sampleMapper.selectById(delegation.getSampleId());
        // 更新委托
        delegation.setState(DelegationStateEnum.SAMPLE_CHECK_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CHECKING_SAMPLE, DelegationStateEnum.SAMPLE_CHECK_SUCCESS,
                (Objects.equals(delegation.getTestingDeptStaffId(), getLoginUserId()) ? "测试部：" : "市场部：") +
                        userService.getUser(getLoginUserId()).getNickname() + " 审核样品通过",
                new HashMap<String, Object>(){{put("delegation", delegation);put("sample", sampleMapper.selectById(sample));}});

        delegation.setState(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.SAMPLE_CHECK_SUCCESS, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                "测试部：" + userService.getUser(delegation.getTestingDeptStaffId()).getNickname() + " 编写测试方案中",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }


    @Override
    public void auditSampleFail(SampleAuditReqVO auditReqVO) {
        // 审核
        DelegationDO delegation = this.auditSample(auditReqVO, false);
        SampleDO sample = sampleMapper.selectById(delegation.getSampleId());
        // 更新委托
        delegation.setState(DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // 更新日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CHECKING_SAMPLE, DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE,
                (Objects.equals(delegation.getTestingDeptStaffId(), getLoginUserId()) ? "测试部：" : "市场部：") +
                        userService.getUser(getLoginUserId()).getNickname() + " 审核样品不通过，请重新修改，原因：" + auditReqVO.getRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);put("sample", sampleMapper.selectById(sample));}});
    }

    @Override
    public void deleteSample(Long id) {
        // 校验存在
        this.validateSampleExists(id);
        // 删除
        sampleMapper.deleteById(id);
    }

    private SampleDO validateSampleExists(Long id) {
        SampleDO sample = sampleMapper.selectById(id);
        if (sample == null) {
            throw exception(SAMPLE_NOT_EXISTS);
        }
        return sample;
    }

    private SampleDO validateSampleState(Long id, SampleStateEnum state) {
        SampleDO sample = this.validateSampleExists(id);
        if (!Objects.equals(sample.getState(), state.getState())) {
            throw exception(SAMPLE_STATE_ERROR);
        }
        return sample;
    }

    private DelegationDO auditSample(SampleAuditReqVO auditReqVO, Boolean success) {
        // 校验存在
        Long sampleId = auditReqVO.getId();
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
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
