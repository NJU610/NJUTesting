package cn.iocoder.yudao.module.system.service.solution;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Lazy;
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
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
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

    @Resource
    @Lazy
    private FlowLogService flowLogService;

    @Resource
    private AdminUserService userService;



    @Override
    public JSONObject getSolutionTable6(String id) {
        return tableMongoRepository.get("table6", id);
    }

    @Override
    public JSONObject getSolutionTable13(String id) {
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
