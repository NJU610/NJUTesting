package cn.iocoder.yudao.module.system.service.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.report.ReportMapper;
import cn.iocoder.yudao.module.system.dal.mysql.solution.SolutionMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.FILE_NOT_EXISTS;
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
    private ContractMapper contractMapper;

    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Resource
    @Lazy
    private FlowLogService flowLogService;

    @Resource
    private AdminUserService userService;

    @Resource
    private FileApi fileApi;

    @Override
    public Long createDelegation(DelegationCreateReqVO createReqVO) {
        Long loginUserId = getLoginUserId();
        // 检验名称是否重复
        this.validateDelegationNameDuplicate(loginUserId, createReqVO.getName());
        // 插入
        Date now = new Date();
        DelegationDO delegation = DelegationConvert.INSTANCE.convert(createReqVO);
        delegation.setLaunchTime(now);
        delegation.setCreatorId(loginUserId);
        delegation.setState(DelegationStateEnum.DELEGATE_WRITING.getState());
        delegationMapper.insert(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                null, DelegationStateEnum.DELEGATE_WRITING,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 创建了委托：" + delegation.getName(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
        // 返回
        return delegation.getId();
    }

    @Override
    public void updateDelegation(DelegationUpdateReqVO updateReqVO) {
        // 校验存在
        delegationMapper.validateDelegationExists(updateReqVO.getId());

        // 检验名称是否重复
        if (updateReqVO.getName() != null) {
            this.validateDelegationNameDuplicate(getLoginUserId(), updateReqVO.getName());
        }
        // 更新
        DelegationDO updateObj = DelegationConvert.INSTANCE.convert(updateReqVO);
        delegationMapper.updateById(updateObj);
    }

    @Override
    public void submitDelegation(DelegationSubmitReqVO submitReqVO) {
        // 校验存在、校验状态
        Long id = submitReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(id,
                DelegationStateEnum.DELEGATE_WRITING,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL,
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL);
        if (delegation.getTable2Id() == null || delegation.getTable3Id() == null) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        DelegationStateEnum initEnum = DelegationStateEnum.getByState(delegation.getState());
        String remark = "";
        // 更新状态，要通过是否有相关字段判断是否是第一次提交，以及目标状态
        if (delegation.getMarketDeptStaffId() == null) {
            delegation.setState(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState());
            remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 提交了委托，等待分配市场部人员";
        } else if (delegation.getTestingDeptStaffId() == null) {
            delegation.setState(DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState());
            remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 提交了委托，等待分配测试部人员";
        } else {
            Integer state = delegation.getState();
            if (Objects.equals(state, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState())) {
                delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState());
                remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 重新提交了委托，市场部审核中";
            } else if (Objects.equals(state, DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState())) {
                delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION.getState());
                remark = "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 重新提交了委托，测试部审核中";
            } else {
                throw exception(DELEGATION_STATE_ERROR);
            }
        }
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                initEnum, DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF,
                remark,
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void saveDelegationTable2(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 保存表单
        if (delegation.getTable2Id() == null) {
            // 没有就新建
            delegation.setTable2Id(tableMongoRepository.create("table2", updateReqVO.getData()));
            delegationMapper.updateById(delegation);
        } else {
            tableMongoRepository.upsert("table2", delegation.getTable2Id(), updateReqVO.getData());
        }
    }

    @Override
    public void saveDelegationTable3(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 保存表单
        if (delegation.getTable3Id() == null) {
            // 没有就新建
            delegation.setTable3Id(tableMongoRepository.create("table3", updateReqVO.getData()));
            delegationMapper.updateById(delegation);
        } else {
            tableMongoRepository.upsert("table3", delegation.getTable3Id(), updateReqVO.getData());
        }
    }

    @Override
    public void saveDelegationTable14(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 保存表单
        if (delegation.getTable14Id() == null) {
            // 没有就新建
            delegation.setTable14Id(tableMongoRepository.create("table14", updateReqVO.getData()));
            delegationMapper.updateById(delegation);
        } else {
            tableMongoRepository.upsert("table14", delegation.getTable14Id(), updateReqVO.getData());
        }
    }

    @Override
    public void saveDelegationTable12(DelegationSaveTableReqVO updateReqVO) {
        // 校验存在
        Long delegationId = updateReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 保存表单
        if (delegation.getTable12Id() == null) {
            // 没有就新建
            delegation.setTable12Id(tableMongoRepository.create("table12", updateReqVO.getData()));
            delegationMapper.updateById(delegation);
        } else {
            tableMongoRepository.upsert("table12", delegation.getTable12Id(), updateReqVO.getData());
        }
    }

    @Override
    public void distributeDelegation2Mkt(DelegationDistributeReqVO distributeReqVO) {
        // 校验存在和状态
        Long delegationId = distributeReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF);
        // 更新接收人员id和状态
        delegation.setMarketDeptStaffId(distributeReqVO.getAcceptorId());
        delegation.setState(DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF, DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 分配了市场部人员：" + userService.getUser(delegation.getMarketDeptStaffId()).getNickname(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void distributeDelegation2Test(DelegationDistributeReqVO distributeReqVO) {
        // 校验存在和状态
        Long delegationId = distributeReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF);
        // 更新接收人员id和状态
        delegation.setTestingDeptStaffId(distributeReqVO.getAcceptorId());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION,
                "测试部：" + userService.getUser(getLoginUserId()).getNickname() + " 分配了测试部人员：" + userService.getUser(delegation.getTestingDeptStaffId()).getNickname(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void auditDelegationSuccessMkt(DelegationAuditReqVO auditReqVO) {
        // 校验存在和状态
        Long delegationId = auditReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注并连续更新状态
        delegation.setMarketRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核委托通过",
                new HashMap<String, Object>(){{put("delegation", delegation);}});


        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS, DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION,
                "测试部：" + userService.getUser(delegation.getTestingDeptStaffId()).getNickname() + " 审核委托中",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void auditDelegationSuccessTest(DelegationAuditReqVO auditReqVO) {
        // 校验存在和状态
        Long delegationId = auditReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION);
        // 确保测试部人员填写了软件文档评审表
        if (delegation.getTable14Id() == null) {
            throw exception(DELEGATION_TABLE14_NOT_FILLED);
        }
        // 更新备注并连续更新状态
        delegation.setTestingRemark(auditReqVO.getRemark());

        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION, DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS,
                "测试部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核委托通过",
                new HashMap<String, Object>(){{put("delegation", delegation);}});


        delegation.setState(DelegationStateEnum.AUDIT_DELEGATION_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS, DelegationStateEnum.AUDIT_DELEGATION_SUCCESS,
                "测试中心：委托审核通过",
                new HashMap<String, Object>(){{put("delegation", delegation);}});

        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.AUDIT_DELEGATION_SUCCESS, DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER,
                "市场部：" + userService.getUser(delegation.getMarketDeptStaffId()).getNickname() + " 生成报价中",
                new HashMap<String, Object>(){{put("delegation", delegation);}});

    }

    @Override
    public void auditDelegationFailMkt(DelegationAuditReqVO auditReqVO) {
        // 校验存在和状态
        Long delegationId = auditReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注和状态
        delegation.setMarketRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION, DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核委托不通过，原因：" + delegation.getMarketRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void auditDelegationFailTest(DelegationAuditReqVO auditReqVO) {
        // 校验存在和状态
        Long delegationId = auditReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION);
        // 更新备注和状态
        delegation.setTestingRemark(auditReqVO.getRemark());
        delegation.setState(DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION, DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL,
                "测试部：" + userService.getUser(getLoginUserId()).getNickname() + " 审核委托不通过，原因：" + delegation.getTestingRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void saveOffer(DelegationSaveTableReqVO offerSaveReqVO) {
        // 校验存在和状态
        Long delegationId = offerSaveReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER,
                DelegationStateEnum.CLIENT_REJECT_OFFER);
        // 保存报价单
        if (delegation.getOfferId() == null) {
            delegation.setOfferId(tableMongoRepository.create("offer", offerSaveReqVO.getData()));
            delegationMapper.updateById(delegation);
        } else {
            tableMongoRepository.upsert("offer", delegation.getOfferId(), offerSaveReqVO.getData());
        }
    }

    @Override
    public void submitOffer(OfferSubmitReqVO offerSubmitReqVO) {
        // 校验存在和状态
        Long delegationId = offerSubmitReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER,
                DelegationStateEnum.CLIENT_REJECT_OFFER);
        DelegationStateEnum fromState = DelegationStateEnum.getByState(delegation.getState());
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_DEALING_OFFER.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                fromState, DelegationStateEnum.CLIENT_DEALING_OFFER,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 提交了报价单",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void rejectOffer(OfferRejectReqVO offerRejectReqVO) {
        // 校验存在和状态
        Long delegationId = offerRejectReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.CLIENT_DEALING_OFFER);
        // 更新拒绝原因和状态
        delegation.setOfferRemark(offerRejectReqVO.getReason());
        delegation.setState(DelegationStateEnum.CLIENT_REJECT_OFFER.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CLIENT_DEALING_OFFER, DelegationStateEnum.CLIENT_REJECT_OFFER,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 拒绝了报价单，原因：" + delegation.getOfferRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void acceptOffer(OfferAcceptReqVO offerAcceptReqVO) {
        // 校验存在和状态
        Long delegationId = offerAcceptReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.CLIENT_DEALING_OFFER);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_ACCEPT_OFFER.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CLIENT_DEALING_OFFER, DelegationStateEnum.CLIENT_ACCEPT_OFFER,
                "客户：" + userService.getUser(getLoginUserId()).getNickname() + " 同意了报价单",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.CLIENT_ACCEPT_OFFER, DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT,
                "市场部：" + userService.getUser(getLoginUserId()).getNickname() + " 生成合同草稿中",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void fillProjectId(DelegationFillProjReqVO reqVO) {
        // 校验存在和状态
        Long delegationId = reqVO.getId();
        String projectId = reqVO.getProjectId();
        DelegationDO delegation = delegationMapper.validateDelegationState(delegationId,
                DelegationStateEnum.WAITING_TESTING_DEPT_MANAGER_FILL_PROJECT_ID);
        // 校验项目编号是否重复
        QueryWrapperX<DelegationDO> queryWrapperX = new QueryWrapperX<>();
        queryWrapperX.eqIfPresent("project_id", projectId);
        if (delegationMapper.selectCount(queryWrapperX) > 0) {
            throw exception(DELEGATION_PROJECT_ID_DUPLICATE);
        }
        // 更新项目编号和状态
        delegation.setProjectId(projectId);
        delegation.setState(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState());
        delegationMapper.updateById(delegation);
        // 更新table2
        tableMongoRepository.upsert("table2", delegation.getTable2Id(), new HashMap<String, Object>(){{
            put("测试项目编号", projectId);
        }});
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                DelegationStateEnum.WAITING_TESTING_DEPT_MANAGER_FILL_PROJECT_ID, DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO,
                "测试部主管：" + userService.getUser(getLoginUserId()).getNickname() + " 填写了项目编号",
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void deleteDelegation(Long id) {
        // 校验存在
        DelegationDO delegation = delegationMapper.validateDelegationExists(id);
        // 删除表格
        if (delegation.getTable2Id() != null) {
            tableMongoRepository.delete("table2", delegation.getTable2Id());
        }
        if (delegation.getTable3Id() != null) {
            tableMongoRepository.delete("table3", delegation.getTable3Id());
        }
        if (delegation.getOfferId() != null) {
            tableMongoRepository.delete("offer", delegation.getOfferId());
        }
        // TODO 删除所有其他附件
        // 删除
        delegationMapper.deleteById(id);
    }

    @Override
    public void addFields(DelegationRespVO respVO) {
        if (respVO == null) return;
        String table2Id = respVO.getTable2Id();
        if (table2Id != null) {
            JSONObject table2 = tableMongoRepository.get("table2", table2Id);
            String softwareName = table2.getString("软件名称");
            String version = table2.getString("版本号");
            String clientUnit = table2.getString("委托单位Ch");
            if (respVO.getSoftwareName() == null) {
                respVO.setSoftwareName(softwareName);
            }
            if (respVO.getVersion() == null) {
                respVO.setVersion(version);
            }
            if (respVO.getClientUnit() == null) {
                respVO.setClientUnit(clientUnit);
            }
        }
    }

    @Override
    public void addFields(List<DelegationRespVO> respVOs) {
        for (DelegationRespVO respVO : respVOs) {
            addFields(respVO);
        }
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

    @Override
    public DelegationDO getDelegation(Long id) {
        return delegationMapper.selectById(id);
    }

    @Override
    public List<DelegationDO> getDelegationsByCurrentUser() {
        Long loginUserId = getLoginUserId();
        return getDelegationsByCreator(loginUserId);
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
    public JSONObject getDelegationTable2(String id) {
        return tableMongoRepository.get("table2", id);
    }

    @Override
    public JSONObject getDelegationTable3(String id) {
        return tableMongoRepository.get("table3", id);
    }

    @Override
    public JSONObject getDelegationTable14(String id) {
        return tableMongoRepository.get("table14", id);
    }

    @Override
    public JSONObject getDelegationTable12(String id) {
        return tableMongoRepository.get("table12", id);
    }

    @Override
    public JSONObject getOffer(String id) {
        return tableMongoRepository.get("offer", id);
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

    public List<FlowLogDO> getDelegationProcessList(Long id) {
        return flowLogService.listLogs(id);
    }

    @Override
    public void cancelDelegationClient(DelegationCancelReqVO delegationCancelReqVO) {
        // 校验委托存在和状态
        Long id = delegationCancelReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(id);
        if (delegation.getState() >= DelegationStateEnum.CONTRACT_SIGN_SUCCESS.getState()) {
            throw exception(DELEGATION_STATE_ERROR);
        }
        // 更新取消原因和状态
        DelegationStateEnum initEnum = DelegationStateEnum.getByState(delegation.getState());
        delegation.setCancelRemark(delegationCancelReqVO.getRemark());
        delegation.setState(DelegationStateEnum.CLIENT_CANCEL_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                initEnum, DelegationStateEnum.CLIENT_CANCEL_DELEGATION,
                "客户取消委托，原因：" + delegationCancelReqVO.getRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public void cancelDelegationAdmin(DelegationCancelReqVO delegationCancelReqVO) {
        // 校验委托存在
        Long id = delegationCancelReqVO.getId();
        DelegationDO delegation = delegationMapper.validateDelegationExists(id);
        // 更新取消原因和状态
        DelegationStateEnum initEnum = DelegationStateEnum.getByState(delegation.getState());
        delegation.setCancelRemark(delegationCancelReqVO.getRemark());
        delegation.setState(DelegationStateEnum.ADMIN_CANCEL_DELEGATION.getState());
        delegationMapper.updateById(delegation);
        // 保存日志
        flowLogService.saveLog(delegation.getId(), getLoginUserId(),
                initEnum, DelegationStateEnum.ADMIN_CANCEL_DELEGATION,
                "管理员取消委托，原因：" + delegationCancelReqVO.getRemark(),
                new HashMap<String, Object>(){{put("delegation", delegation);}});
    }

    @Override
    public String exportTable(DelegationExportTableReqVO exportTableReqVO) throws IOException {
        // 校验存在
        Long delegationId = exportTableReqVO.getDelegationId();
        String tableName = exportTableReqVO.getTableName();
        String tableId = null;
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 构建PDFRequestVO
        PDFRequestVO pdfRequestVO = new PDFRequestVO();
        pdfRequestVO.setTableName(tableName);
        // 获取表格编号
        if (Objects.equals(tableName, "table2")) {
            tableId = delegation.getTable2Id();
        } else if (Objects.equals(tableName, "table3")) {
            tableId = delegation.getTable3Id();
        } else if (Objects.equals(tableName, "table12")) {
            tableId = delegation.getTable12Id();
        } else if (Objects.equals(tableName, "table14")) {
            tableId = delegation.getTable14Id();
        } else if (Objects.equals(tableName, "offer")) {
            tableId = delegation.getOfferId();
        } else if (Objects.equals(tableName, "table4") || Objects.equals(tableName, "table5")) {
            if (delegation.getContractId() == null) {
                throw exception(TABLE_NOT_EXISTS);
            }
            ContractDO contract = contractMapper.selectById(delegation.getContractId());
            if (contract == null) {
                throw exception(CONTRACT_NOT_EXISTS);
            }
            if (Objects.equals(tableName, "table4")) {
                tableId = contract.getTable4Id();
            } else if (Objects.equals(tableName, "table5")) {
                tableId = contract.getTable5Id();
            }
        } else if (Objects.equals(tableName, "table6") || Objects.equals(tableName, "table13")) {
            if (delegation.getSolutionId() == null) {
                throw exception(SOLUTION_NOT_EXISTS);
            }
            SolutionDO solution = solutionMapper.selectById(delegation.getSolutionId());
            if (solution == null) {
                throw exception(SOLUTION_NOT_EXISTS);
            }
            if (Objects.equals(tableName, "table6")) {
                tableId = solution.getTable6Id();
            } else if (Objects.equals(tableName, "table13")) {
                tableId = solution.getTable13Id();
            }
        } else if (Objects.equals(tableName, "table7") || Objects.equals(tableName, "table8") ||
                Objects.equals(tableName, "table9") || Objects.equals(tableName, "table10") ||
                Objects.equals(tableName, "table11")) {
            if (delegation.getReportId() == null) {
                throw exception(REPORT_NOT_EXISTS);
            }
            ReportDO report = reportMapper.selectById(delegation.getReportId());
            if (report == null) {
                throw exception(REPORT_NOT_EXISTS);
            }
            switch (tableName) {
                case "table7":
                    tableId = report.getTable7Id();
                    break;
                case "table8":
                    tableId = report.getTable8Id();
                    break;
                case "table9":
                    tableId = report.getTable9Id();
                    break;
                case "table10":
                    tableId = report.getTable10Id();
                    break;
                case "table11":
                    tableId = report.getTable11Id();
                    break;
            }
        }
        if (tableId == null) {
            throw exception(TABLE_NOT_EXISTS);
        }
        pdfRequestVO.setTableId(tableId);
        return exportPDFOfTable(pdfRequestVO);
    }

    @Override
    public String exportPDFOfTable(PDFRequestVO pdfRequestVO) throws IOException {
        // 获取传入参数
        String tableId = pdfRequestVO.getTableId();
        String tableName = pdfRequestVO.getTableName();

        // 为文件生成一个带信息的id
        String prefix = tableName + "_" + tableId + "_" + System.currentTimeMillis();

        // 获取表格数据
        JSONObject jsonObject = tableMongoRepository.get(tableName, tableId);
        if (jsonObject == null) {
            throw exception(TABLE_NOT_EXISTS);
        }
        String json = jsonObject.toJSONString();

        // 获取生成文件路径的根目录
        //ClassPathResource classPathResource = new ClassPathResource("/tool");
        String rootPath = "/root/.jenkins/workspace/njutesting/yudao-server/src/main/resources/tool";
        //rootPath = classPathResource.getFile().getAbsolutePath();
        System.out.println(rootPath);

        // 将json写入文件
        File newFile = new File(rootPath, prefix + ".json");
        assert newFile.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(newFile);
        outputStream.write(json.getBytes());
        outputStream.close();

        // 生成文件的脚本地址
        HashMap<String, String> map = new HashMap<String, String>(){{
            put("table2", "JS002");
            put("table3", "JS003");
            put("table4", "JS004");
            put("table5", "JS005");
            put("table6", "JS006");
            put("table7", "JS007");
            put("table8", "JS008");
            put("table9", "JS009");
            put("table10", "JS010");
            put("table11", "JS011");
            put("table12", "JS012");
            put("table13", "JS013");
            put("table14", "JS014");
            put("offer", "offer");

        }};
        String script_path = map.get(tableName);

        // 获得模板名称
        HashMap<String, String> map2 = new HashMap<String, String>(){{
            put("table2", "NST－04－JS002－2011－软件项目委托测试申请表-空白表.docx");
            put("table3", "NST－04－JS003－2011－委托测试软件功能列表.docx");
            put("table4", "NST－04－JS004－2011－软件委托测试合同.docx");
            put("table5", "NST－04－JS005－2011－软件项目委托测试保密协议.docx");
            put("table6", "NST－04－JS006－2011－软件测试方案.docx");
            put("table7", "NST－04－JS007－2011－软件测试报告.docx");
            put("table8", "NST－04－JS008－2011－测试用例（电子记录）.xlsx");
            put("table9", "NST－04－JS009－2011－软件测试记录（电子记录）.xlsx");
            put("table10", "NST－04－JS010－2011－测试报告检查表.docx");
            put("table11", "NST－04－JS011－2011－软件测试问题清单（电子记录）.xlsx");
            put("table12", "NST－04－JS012－2011－软件项目委托测试工作检查表.docx");
            put("table13", "NST－04－JS013－2011-测试方案评审表.docx");
            put("table14", "NST－04－JS014－2011-软件文档评审表.docx");
            put("offer", "报价单.docx");

        }};
        String template_name = map2.get(tableName);

        // 执行pdf生成脚本
        Process proc;
        try {
            String command = "python3 " +
                    rootPath + File.separator + script_path + File.separator + "test.py" + " " +
                    "-t " + rootPath + File.separator + script_path + File.separator + template_name + " " +
                    "-i " + rootPath + File.separator + prefix + ".json" + " " +
                    "-o " + rootPath + File.separator + prefix;
            System.out.println(command);
            proc = Runtime.getRuntime().exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // 写入文件
        String type;
        String result;
        if (tableName.equals("table8") || tableName.equals("table9") || tableName.equals("table11")) {
            type = ".xls";
        } else {
            type = ".pdf";
        }
        String filePath = rootPath + File.separator + prefix + type;
        File file = new File(filePath);
        if (!file.exists()) {
            throw exception(FILE_NOT_EXISTS);
        }
        try {
            result = fileApi.createFile( prefix + type , Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 删除临时文件
        List<String> deleteList = new ArrayList<String>(){{
            add(".docx");
            add(".xlsx");
            add(".pdf");
            add(".json");
        }};
        for(String delete : deleteList) {
            File deleteFile = new File(rootPath + File.separator + prefix + delete);
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }

        return result;
    }

    public String exportTableImply(DelegationExportTableReqVO exportTableReqVO) throws IOException {
        // 校验存在
        Long delegationId = exportTableReqVO.getDelegationId();
        String tableName = exportTableReqVO.getTableName();
        String tableId = null;
        DelegationDO delegation = delegationMapper.validateDelegationExists(delegationId);
        // 构建PDFRequestVO
        PDFRequestVO pdfRequestVO = new PDFRequestVO();
        pdfRequestVO.setTableName(tableName);
        // 获取表格编号
        if (Objects.equals(tableName, "table2")) {
            tableId = delegation.getTable2Id();
        } else if (Objects.equals(tableName, "table3")) {
            tableId = delegation.getTable3Id();
        } else if (Objects.equals(tableName, "table12")) {
            tableId = delegation.getTable12Id();
        } else if (Objects.equals(tableName, "table14")) {
            tableId = delegation.getTable14Id();
        } else if (Objects.equals(tableName, "offer")) {
            tableId = delegation.getOfferId();
        } else if (Objects.equals(tableName, "table4") || Objects.equals(tableName, "table5")) {
            if (delegation.getContractId() == null) {
                throw exception(TABLE_NOT_EXISTS);
            }
            ContractDO contract = contractMapper.selectById(delegation.getContractId());
            if (contract == null) {
                throw exception(CONTRACT_NOT_EXISTS);
            }
            if (Objects.equals(tableName, "table4")) {
                tableId = contract.getTable4Id();
            } else if (Objects.equals(tableName, "table5")) {
                tableId = contract.getTable5Id();
            }
        } else if (Objects.equals(tableName, "table6") || Objects.equals(tableName, "table13")) {
            if (delegation.getSolutionId() == null) {
                throw exception(SOLUTION_NOT_EXISTS);
            }
            SolutionDO solution = solutionMapper.selectById(delegation.getSolutionId());
            if (solution == null) {
                throw exception(SOLUTION_NOT_EXISTS);
            }
            if (Objects.equals(tableName, "table6")) {
                tableId = solution.getTable6Id();
            } else if (Objects.equals(tableName, "table13")) {
                tableId = solution.getTable13Id();
            }
        } else if (Objects.equals(tableName, "table7") || Objects.equals(tableName, "table8") ||
                Objects.equals(tableName, "table9") || Objects.equals(tableName, "table10") ||
                Objects.equals(tableName, "table11")) {
            if (delegation.getReportId() == null) {
                throw exception(REPORT_NOT_EXISTS);
            }
            ReportDO report = reportMapper.selectById(delegation.getReportId());
            if (report == null) {
                throw exception(REPORT_NOT_EXISTS);
            }
            switch (tableName) {
                case "table7":
                    tableId = report.getTable7Id();
                    break;
                case "table8":
                    tableId = report.getTable8Id();
                    break;
                case "table9":
                    tableId = report.getTable9Id();
                    break;
                case "table10":
                    tableId = report.getTable10Id();
                    break;
                case "table11":
                    tableId = report.getTable11Id();
                    break;
            }
        }
        if (tableId == null) {
            throw exception(TABLE_NOT_EXISTS);
        }
        pdfRequestVO.setTableId(tableId);
        return exportPDFOfTable(pdfRequestVO);
    }

}
