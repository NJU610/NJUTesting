package cn.iocoder.yudao.module.system.dal.mysql.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.DelegationExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.DelegationPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 委托 Mapper
 *
 * @author lyw
 */
@Mapper
public interface DelegationMapper extends BaseMapperX<DelegationDO> {

    default PageResult<DelegationDO> selectPage(DelegationPageReqVO reqVO) {
        if (reqVO.getOrderField() == null) {
            reqVO.setOrderField("id");
        }
        if (reqVO.getAsc() == null) {
            reqVO.setAsc(false);
        }
        QueryWrapperX<DelegationDO> queryWrapper = new QueryWrapperX<DelegationDO>()
                .eqIfPresent("creator_id", reqVO.getCreatorId())
                .betweenIfPresent("launch_time", reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("table2_id", reqVO.getTable2Id())
                .eqIfPresent("table3_id", reqVO.getTable3Id())
                .eqIfPresent("market_dept_staff_id", reqVO.getMarketDeptStaffId())
                .eqIfPresent("testing_dept_staff_id", reqVO.getTestingDeptStaffId())
                .eqIfPresent("table14_id", reqVO.getTable14Id())
                .eqIfPresent("offer_id", reqVO.getOfferId())
                .eqIfPresent("contract_id", reqVO.getContractId())
                .eqIfPresent("sample_id", reqVO.getSampleId())
                .eqIfPresent("solution_id", reqVO.getSolutionId())
                .eqIfPresent("report_id", reqVO.getReportId())
                .eqIfPresent("project_id", reqVO.getProjectId())
                .inIfPresent("state", reqVO.getState())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime());
        if (reqVO.getAsc()) {
            queryWrapper.orderByAsc(reqVO.getOrderField());
        } else {
            queryWrapper.orderByDesc(reqVO.getOrderField());
        }
        return selectPage(reqVO, queryWrapper);
    }

    default List<DelegationDO> selectList(DelegationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DelegationDO>()
                .eqIfPresent(DelegationDO::getCreatorId, reqVO.getCreatorId())
                .betweenIfPresent(DelegationDO::getLaunchTime, reqVO.getBeginLaunchTime(), reqVO.getEndLaunchTime())
                .likeIfPresent(DelegationDO::getName, reqVO.getName())
                .eqIfPresent(DelegationDO::getTable2Id, reqVO.getTable2Id())
                .eqIfPresent(DelegationDO::getTable3Id, reqVO.getTable3Id())
                .eqIfPresent(DelegationDO::getMarketDeptStaffId, reqVO.getMarketDeptStaffId())
                .eqIfPresent(DelegationDO::getTestingDeptStaffId, reqVO.getTestingDeptStaffId())
                .eqIfPresent(DelegationDO::getTable14Id, reqVO.getTable14Id())
                .eqIfPresent(DelegationDO::getOfferId, reqVO.getOfferId())
                .eqIfPresent(DelegationDO::getContractId, reqVO.getContractId())
                .eqIfPresent(DelegationDO::getSampleId, reqVO.getSampleId())
                .eqIfPresent(DelegationDO::getSolutionId, reqVO.getSolutionId())
                .eqIfPresent(DelegationDO::getReportId, reqVO.getReportId())
                .eqIfPresent(DelegationDO::getState, reqVO.getState())
                .betweenIfPresent(DelegationDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(DelegationDO::getId));
    }

    default DelegationDO validateDelegationExists(Long id) {
        DelegationDO delegation = selectById(id);
        if (delegation == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
        if (delegation.getCancelRemark() != null) {
            throw exception(DELEGATION_CANCELED);
        }
        return delegation;
    }

    default DelegationDO validateDelegationState(DelegationDO delegation, DelegationStateEnum ... states) {
        if (delegation == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
        if (delegation.getCancelRemark() != null) {
            throw exception(DELEGATION_CANCELED);
        }
        List<Integer> list = Arrays.stream(states).map(DelegationStateEnum::getState).collect(Collectors.toList());
        if (!list.contains(delegation.getState())) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        return delegation;
    }

    default DelegationDO validateDelegationState(Long id, DelegationStateEnum ... states) {
        DelegationDO delegation = selectById(id);
        return validateDelegationState(delegation, states);
    }

    default DelegationDO validateDelegationStateByContract(Long contractId, DelegationStateEnum ... states) {
        DelegationDO delegation = selectOne("contract_id", contractId);
        return validateDelegationState(delegation, states);
    }

    default DelegationDO validateDelegationStateBySample(Long sampleId, DelegationStateEnum ... states) {
        DelegationDO delegation = selectOne("sample_id", sampleId);
        return validateDelegationState(delegation, states);
    }

    default DelegationDO validateDelegationStateBySolution(Long solutionId, DelegationStateEnum ... states) {
        DelegationDO delegation = selectOne("solution_id", solutionId);
        return validateDelegationState(delegation, states);
    }

    default DelegationDO validateDelegationStateByReport(Long reportId, DelegationStateEnum ... states) {
        DelegationDO delegation = selectOne("report_id", reportId);
        return validateDelegationState(delegation, states);
    }

    default DelegationDO selectByProject(Long projectId) {
        return selectOne("project_id", projectId);

    }
}
