package cn.iocoder.yudao.module.system.service.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 委托 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class DelegationServiceImpl implements DelegationService {

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Override
    public Long createDelegation(DelegationCreateReqVO createReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        // 检验名称是否重复
        this.validateDelegationNameDuplicate(loginUserId, createReqVO.getName());
        // 插入
        Date now = new Date();
        DelegationDO delegation = DelegationConvert.INSTANCE.convert(createReqVO);
        delegation.setLaunchTime(now);
        delegation.setCreatorId(loginUserId);
        delegation.setState(DelegationStateEnum.DELEGATE_WRITING.getState());
        delegationMapper.insert(delegation);
        // TODO 保存日志
        // 返回
        return delegation.getId();
    }

    @Override
    public void updateDelegation(DelegationUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDelegationExists(updateReqVO.getId());

        // 检验名称是否重复
        if (updateReqVO.getName() != null) {
            this.validateDelegationNameDuplicate(SecurityFrameworkUtils.getLoginUserId(), updateReqVO.getName());
        }
        // 更新
        DelegationDO updateObj = DelegationConvert.INSTANCE.convert(updateReqVO);
        delegationMapper.updateById(updateObj);
    }

    @Override
    public void submitDelegation(DelegationSubmitReqVO submitReqVO) {
        // 校验存在
        Long id = submitReqVO.getId();
        this.validateDelegationExists(id);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(id, DelegationStateEnum.DELEGATE_WRITING);
        // 更新状态，要通过是否有相关字段判断是否是第一次提交，以及目标状态
        if (delegation.getMarketDeptStaffId() == null) {
            delegation.setState(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState());
        } else if (delegation.getTestingDeptStaffId() == null) {
            delegation.setState(DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState());
        } else {
            delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState());
        }
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void saveDelegationTable2(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);
        if (delegationDO.getTable2Id() == null) {
            delegationDO.setTable2Id(tableMongoRepository.create("table2", updateReqVO.getData()));
        } else {
            tableMongoRepository.upsert("table2", delegationDO.getTable2Id(), updateReqVO.getData());
        }

        delegationMapper.updateById(delegationDO);
    }

    @Override
    public void saveDelegationTable3(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);
        if (delegationDO.getTable3Id() == null) {
            delegationDO.setTable3Id(tableMongoRepository.create("table3", updateReqVO.getData()));
        } else {
            tableMongoRepository.upsert("table3", delegationDO.getTable3Id(), updateReqVO.getData());
        }

        delegationMapper.updateById(delegationDO);
    }

    @Override
    public void saveDelegationTable14(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);
        if (delegationDO.getTable14Id() == null) {
            delegationDO.setTable14Id(tableMongoRepository.create("table14", updateReqVO.getData()));
        } else {
            tableMongoRepository.upsert("table14", delegationDO.getTable14Id(), updateReqVO.getData());
        }

        delegationMapper.updateById(delegationDO);
    }

    @Override
    public void distributeDelegation2Mkt(DelegationDistributeReqVO distributeReqVO) {
        // 校验存在
        Long delegationId = distributeReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF);
        // 更新接收人员id和状态
        delegation.setMarketDeptStaffId(distributeReqVO.getAcceptorId());
        delegation.setState(DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void distributeDelegation2Test(DelegationDistributeReqVO distributeReqVO) {
        // 校验存在
        Long delegationId = distributeReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF);
        // 更新接收人员id和状态
        delegation.setTestingDeptStaffId(distributeReqVO.getAcceptorId());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditDelegationSuccessMkt(DelegationAuditReqVO auditReqVO) {
        // 校验存在
        Long delegationId = auditReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注并连续更新状态
        delegation.setMarketRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditDelegationSuccessTest(DelegationAuditReqVO auditReqVO) {
        // 校验存在
        Long delegationId = auditReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION);
        // 确保测试部人员填写了软件文档评审表
        if (delegation.getTable14Id() == null) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        // 更新备注并连续更新状态
        delegation.setTestingRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.AUDIT_DELEGATION_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditDelegationFailMkt(DelegationAuditReqVO auditReqVO) {
        // 校验存在
        Long delegationId = auditReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注并连续更新状态
        delegation.setMarketRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
        delegation.setState(DelegationStateEnum.DELEGATE_WRITING.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void auditDelegationFailTest(DelegationAuditReqVO auditReqVO) {
        // 校验存在
        Long delegationId = auditReqVO.getId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注并连续更新状态
        delegation.setTestingRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
        delegation.setState(DelegationStateEnum.DELEGATE_WRITING.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public String createOffer(OfferCreateReqVO offerCreateReqVO) {
        // 校验存在
        Long delegationId = offerCreateReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER);
        if (delegation.getTable14Id() != null) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        // 创建报价单
        String tableId = tableMongoRepository.create("table14", offerCreateReqVO.getData());
        // 更新委托
        delegation.setOfferId(tableId);
        delegation.setState(DelegationStateEnum.CLIENT_DEALING_OFFER.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
        return tableId;
    }

    @Override
    public void updateOffer(OfferUpdateReqVO offerUpdateReqVO) {
        // 校验状态
        DelegationDO delegation = delegationMapper.selectOne("offer_id", offerUpdateReqVO.getId());
        if (delegation == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
        if (!Objects.equals(delegation.getState(), DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState())) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        tableMongoRepository.upsert("table14", offerUpdateReqVO.getId(), offerUpdateReqVO.getData());
    }

    @Override
    public void rejectOffer(OfferRejectReqVO offerRejectReqVO) {
        // 校验存在
        Long delegationId = offerRejectReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.CLIENT_DEALING_OFFER);
        // 更新拒绝原因并连续更新状态
        delegation.setOfferRemark(offerRejectReqVO.getReason());
        delegation.setState(DelegationStateEnum.CLIENT_REJECT_OFFER.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void acceptOffer(OfferAcceptReqVO offerAcceptReqVO) {
        // 校验存在
        Long delegationId = offerAcceptReqVO.getDelegationId();
        this.validateDelegationExists(delegationId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.CLIENT_DEALING_OFFER);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_ACCEPT_OFFER.getState());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void deleteDelegation(Long id) {
        // 校验存在
        this.validateDelegationExists(id);
        // 删除表格
        DelegationDO delegationDO = delegationMapper.selectById(id);
        if (delegationDO.getTable2Id() != null) {
            tableMongoRepository.delete("table2", delegationDO.getTable2Id());
        }
        if (delegationDO.getTable3Id() != null) {
            tableMongoRepository.delete("table3", delegationDO.getTable3Id());
        }
        // 删除
        delegationMapper.deleteById(id);
    }

    //判断委托名称不重复
    public void validateDelegationNameDuplicate(Long creatorId, String name) {
        QueryWrapperX<DelegationDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("creator_id", creatorId)
                .eqIfPresent("name", name)
                .eqIfPresent("deleted", false);
        if (delegationMapper.selectCount(queryWrapper) > 0) {
            throw exception(DELEGATION_NAME_DUPLICATE);
        }
    }

    private void validateDelegationExists(Long id) {
        if (delegationMapper.selectById(id) == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
    }
    
    @Override
    public DelegationDO getDelegation(Long id) {
        return delegationMapper.selectById(id);
    }

    @Override
    public List<DelegationDO> getDelegationsByCurrentUser() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        return delegationMapper.selectList("creator_id", loginUserId);
    }

    @Override
    public List<DelegationDO> getDelegationsByCreator(Long creatorId) {
        return delegationMapper.selectList("creator_id", creatorId);
    }

    @Override
    public List<DelegationDO> getDelegationsNotAccepted() {
        QueryWrapperX<DelegationDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("deleted", false)
                .isNull("acceptor_id");
        return delegationMapper.selectList(queryWrapper);
    }

    @Override
    public String getDelegationTable2(String id) {
        return tableMongoRepository.get("table2", id);
    }

    @Override
    public String getDelegationTable3(String id) {
        return tableMongoRepository.get("table3", id);
    }

    @Override
    public String getDelegationTable14(String id) {
        return tableMongoRepository.get("table14", id);
    }

    @Override
    public List<DelegationDO> getDelegationList(Collection<Long> ids) {
        return delegationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DelegationDO> getDelegationPage(DelegationPageReqVO pageReqVO) {
        return delegationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DelegationDO> getDelegationList(DelegationExportReqVO exportReqVO) {
        return delegationMapper.selectList(exportReqVO);
    }

}
