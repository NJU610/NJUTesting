package cn.iocoder.yudao.module.system.service.solution;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.solution.SolutionConvert;
import cn.iocoder.yudao.module.system.dal.mysql.solution.SolutionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 测试方案 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class SolutionServiceImpl implements SolutionService {

    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Override
    public void saveSolutionTable6(SolutionSaveTableReqVO saveReqVO) {
        // 校验测试方案是否存在
        Long solutionId = saveReqVO.getSolutionId();
        SolutionDO solution = this.validateSolutionExists(solutionId);
        // 保存表单
        if (solution.getTable6Id() == null) {
            solution.setTable6Id(tableMongoRepository.create("table6", saveReqVO.getData()));
            solutionMapper.updateById(solution);
        } else {
            tableMongoRepository.upsert("table6", solution.getTable6Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveSolutionTable13(SolutionSaveTableReqVO saveReqVO) {
        // 校验测试方案是否存在
        Long solutionId = saveReqVO.getSolutionId();
        SolutionDO solution = this.validateSolutionExists(solutionId);
        // 保存表单
        if (solution.getTable13Id() == null) {
            solution.setTable13Id(tableMongoRepository.create("table13", saveReqVO.getData()));
            solutionMapper.updateById(solution);
        } else {
            tableMongoRepository.upsert("table13", solution.getTable13Id(), saveReqVO.getData());
        }
    }

    @Override
    public void submitSolutionTable6(SolutionSubmitReqVO submitReqVO) {
        // 校验测试方案是否存在
        Long solutionId = submitReqVO.getSolutionId();
        SolutionDO solution = this.validateSolutionExists(solutionId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateBySolution(solutionId,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION_FAIL);
        // 更新状态
        delegation.setState(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditSuccess(SolutionSubmitReqVO submitReqVO) {
        // 校验状态
        DelegationDO delegation = auditSolution(submitReqVO);
        // 连续更新状态
        delegation.setState(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditFail(SolutionSubmitReqVO submitReqVO) {
        // 校验状态
        DelegationDO delegation = auditSolution(submitReqVO);
        // 更新状态
        delegation.setState(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION_FAIL.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public String getSolutionTable6(String id) {
        return tableMongoRepository.get("table6", id);
    }

    @Override
    public String getSolutionTable13(String id) {
        return tableMongoRepository.get("table13", id);
    }

    @Override
    public void updateSolution(SolutionUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSolutionExists(updateReqVO.getId());
        // 更新
        SolutionDO updateObj = SolutionConvert.INSTANCE.convert(updateReqVO);
        solutionMapper.updateById(updateObj);
    }

    @Override
    public void deleteSolution(Long id) {
        // 校验存在
        this.validateSolutionExists(id);
        // 删除
        solutionMapper.deleteById(id);
    }

    private SolutionDO validateSolutionExists(Long id) {
        SolutionDO solution = solutionMapper.selectById(id);
        if (solution == null) {
            throw exception(SOLUTION_NOT_EXISTS);
        }
        return solution;
    }

    private DelegationDO auditSolution(SolutionSubmitReqVO submitReqVO) {
        // 校验测试方案是否存在
        Long solutionId = submitReqVO.getSolutionId();
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        SolutionDO solution = this.validateSolutionExists(solutionId);
        // 校验状态
        if (solution.getTable13Id() == null) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        DelegationDO delegation = delegationMapper.validateDelegationStateBySolution(solutionId,
                DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION);
        // 更新审核员
        solution.setAuditorId(loginUserId);
        solutionMapper.updateById(solution);
        return delegation;
    }

    @Override
    public SolutionDO getSolution(Long id) {
        return solutionMapper.selectById(id);
    }

    @Override
    public List<SolutionDO> getSolutionList(Collection<Long> ids) {
        return solutionMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SolutionDO> getSolutionPage(SolutionPageReqVO pageReqVO) {
        return solutionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SolutionDO> getSolutionList(SolutionExportReqVO exportReqVO) {
        return solutionMapper.selectList(exportReqVO);
    }

}
