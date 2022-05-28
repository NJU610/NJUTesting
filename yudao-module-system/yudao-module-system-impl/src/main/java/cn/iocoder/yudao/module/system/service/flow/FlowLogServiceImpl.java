package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.flow.FlowLogConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowLogMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.FLOW_LOG_NOT_EXISTS;

/**
 * 流程操作 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class FlowLogServiceImpl implements FlowLogService {

    @Resource
    private FlowLogMapper flowLogMapper;

    @Resource
    private DelegationService delegationService;

    @Resource
    private ContractService contractService;

    @Resource
    private AdminUserService userService;

    public Long saveLog (Long delegationId, Long operatorId,
                         DelegationStateEnum fromState, DelegationStateEnum toState,
                         String remark, Map<String, Object> mapValue) {;

        // 将 mapValue 转换为 json
        String stringMapValue;
        try {
            stringMapValue = mapValue == null ? null : new ObjectMapper().writeValueAsString(mapValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 创建流程操作记录
        FlowLogDO flowLogDO = new FlowLogDO()
                .setDelegationId(delegationId)
                .setFromState(Optional.ofNullable(fromState).map(DelegationStateEnum::getState).orElse(null))
                .setToState(Optional.ofNullable(toState).map(DelegationStateEnum::getState).orElse(null))
                .setOperatorId(operatorId)
                .setOperateTime(new Date())
                .setRemark(remark)
                .setMapValue(stringMapValue);

        // 插入流程操作记录
        flowLogMapper.insert(flowLogDO);

        return flowLogDO.getId();

    }
    public List<FlowLogDO> listLogs(Long delegationId) {
        return flowLogMapper.selectList("delegation_id", delegationId);
    }

    @Override
    public Long createFlowLog(FlowLogCreateReqVO createReqVO) {
        // 插入
        FlowLogDO flowLog = FlowLogConvert.INSTANCE.convert(createReqVO);
        flowLogMapper.insert(flowLog);
        // 返回
        return flowLog.getId();
    }

    @Override
    public void updateFlowLog(FlowLogUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateFlowLogExists(updateReqVO.getId());
        // 更新
        FlowLogDO updateObj = FlowLogConvert.INSTANCE.convert(updateReqVO);
        flowLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteFlowLog(Long id) {
        // 校验存在
        this.validateFlowLogExists(id);
        // 删除
        flowLogMapper.deleteById(id);
    }

    private void validateFlowLogExists(Long id) {
        if (flowLogMapper.selectById(id) == null) {
            throw exception(FLOW_LOG_NOT_EXISTS);
        }
    }

    @Override
    public FlowLogDO getFlowLog(Long id) {
        return flowLogMapper.selectById(id);
    }

    @Override
    public List<FlowLogDO> getFlowLogList(Collection<Long> ids) {
        return flowLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FlowLogDO> getFlowLogPage(FlowLogPageReqVO pageReqVO) {
        return flowLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FlowLogDO> getFlowLogList(FlowLogExportReqVO exportReqVO) {
        return flowLogMapper.selectList(exportReqVO);
    }
}
