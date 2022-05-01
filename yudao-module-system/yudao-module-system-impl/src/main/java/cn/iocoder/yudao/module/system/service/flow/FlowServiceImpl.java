package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.flow.FlowConvert;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 项目流程 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class FlowServiceImpl implements FlowService {

    @Resource
    private FlowMapper flowMapper;

    @Override
    public Long createFlow(FlowCreateVO createReqVO) {
        // 插入
        FlowDO flow = FlowConvert.INSTANCE.convert(createReqVO);
        flowMapper.insert(flow);
        // 返回
        return flow.getId();
    }

    @Override
    public void updateFlow(FlowUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateFlowExists(updateReqVO.getId());
        // 更新
        FlowDO updateObj = FlowConvert.INSTANCE.convert(updateReqVO);
        flowMapper.updateById(updateObj);
    }

    @Override
    public void deleteFlowByCreator(Long delegationId) {
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.eqIfPresent("delegation_id", delegationId);
        flowMapper.delete(queryWrapper);
    }

    private void validateFlowExists(Long id) {
        if (flowMapper.selectById(id) == null) {
            throw exception(FLOW_NOT_EXISTS);
        }
    }

    @Override
    public List<FlowDO> getFlowsByCondition(Map<String, Object> condition) {
        QueryWrapperX<FlowDO> queryWrapper = new QueryWrapperX<>();
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            if (entry.getValue() == null) {;
                queryWrapper.isNull(entry.getKey());
            } else {
                queryWrapper.eq(entry.getKey(), entry.getValue());
            }
        }
        return flowMapper.selectList(queryWrapper);
    }

    @Override
    public List<FlowDO> getAllFlows() {
        return flowMapper.selectList();
    }

    @Override
    public List<FlowDO> getFlowList(Collection<Long> ids) {
        return flowMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FlowDO> getFlowPage(FlowPageReqVO pageReqVO) {
        return flowMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FlowDO> getFlowList(FlowExportReqVO exportReqVO) {
        return flowMapper.selectList(exportReqVO);
    }

}
