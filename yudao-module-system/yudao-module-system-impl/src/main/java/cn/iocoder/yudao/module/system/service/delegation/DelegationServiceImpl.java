package cn.iocoder.yudao.module.system.service.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowCreateVO;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.convert.flow.FlowConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowMapper;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

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
    private FlowMapper flowMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Override
    public Long createDelegation(DelegationCreateReqVO createReqVO) {
        // 检验名称是否重复
        if (createReqVO.getName() != null) {
            this.validateDelegationNameDuplicate(SecurityFrameworkUtils.getLoginUserId(), createReqVO.getName());
        }

        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Date now = new Date();
        // 插入
        DelegationDO delegation = DelegationConvert.INSTANCE.convert(createReqVO);
        delegation.setLaunchTime(now);
        delegation.setCreatorId(loginUserId);
        delegationMapper.insert(delegation);
        // 插入流程
        FlowCreateVO flowCreateVO = FlowCreateVO.builder()
                .delegationId(delegation.getId())
                .creatorId(loginUserId)
                .launchTime(now)
                .build();
        FlowDO flow = FlowConvert.INSTANCE.convert(flowCreateVO);
        flow.setState(FlowStateEnum.DELEGATE_WRITING.getState());
        flowMapper.insert(flow);
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
    public void submitDelegation(Long id) {
        // 校验存在
        this.validateDelegationExists(id);

        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("delegation_id", id)
                .eqIfPresent("deleted", false);
        FlowDO flowDO = flowMapper.selectOne(queryWrapper);
        flowDO.setState(FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE.getState());
        flowMapper.updateById(flowDO);
    }

    @Override
    public void saveDelegationTable2(DelegationSaveTableReqVo updateReqVO) {
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
    public void saveDelegationTable3(DelegationSaveTableReqVo updateReqVO) {
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
    public DelegationRespVO getDelegation(Long id) {
        // 校验存在
        this.validateDelegationExists(id);
        return convert(delegationMapper.selectById(id));
    }

    @Override
    public List<DelegationRespVO> getDelegationsByCurrentUser() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        return convert(delegationMapper.selectList("creator_id", loginUserId));
    }

    @Override
    public List<DelegationRespVO> getDelegationsByCreator(Long creatorId) {
        return convert(delegationMapper.selectList("creator_id", creatorId));
    }

    @Override
    public List<DelegationRespVO> getDelegationsNotAccepted() {
        QueryWrapperX<DelegationDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("deleted", false)
                .isNull("acceptor_id");
        return convert(delegationMapper.selectList(queryWrapper));
    }

    private DelegationRespVO convert(DelegationDO delegationDO) {
        if (delegationDO == null) {
            return null;
        }
        DelegationRespVO delegationRespVO = DelegationConvert.INSTANCE.convert(delegationDO);
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("delegation_id", delegationDO.getId())
                .eqIfPresent("deleted", false);
        FlowDO flowDO = flowMapper.selectOne(queryWrapper);
        delegationRespVO.setState(flowDO.getState());
        return delegationRespVO;
    }

    private List<DelegationRespVO> convert(List<DelegationDO> delegationDOList) {
        if (delegationDOList == null) {
            return null;
        }
        List<DelegationRespVO> delegationRespVOList = new ArrayList<>();
        for (DelegationDO delegationDO : delegationDOList) {
            delegationRespVOList.add(convert(delegationDO));
        }
        return delegationRespVOList;
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
    public List<DelegationRespVO> getDelegationList(Collection<Long> ids) {
        return convert(delegationMapper.selectBatchIds(ids));
    }

    @Override
    public PageResult<DelegationRespVO> getDelegationPage(DelegationPageReqVO pageReqVO) {
        // 获取分页结果
        PageResult<DelegationDO> pageDOResult = delegationMapper.selectPage(pageReqVO);
        List<DelegationDO> list = pageDOResult.getList();
        // 转换
        PageResult<DelegationRespVO> pageResult = new PageResult<>();
        List<DelegationRespVO> delegationRespVOList = convert(list);
        pageResult.setList(delegationRespVOList);
        pageResult.setTotal(pageDOResult.getTotal());
        return pageResult;
    }

    @Override
    public List<DelegationDO> getDelegationList(DelegationExportReqVO exportReqVO) {
        return delegationMapper.selectList(exportReqVO);
    }

}
