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

    public Long saveLogByProject (Long ProjectId,
                           Long operatorId,
                           DelegationStateEnum fromState,
                           DelegationStateEnum toState,
                           String remark,
                           Map<String, Object> mapValue){
        // 将 mapValue 转换为 json
        String stringMapValue;
        try {
            stringMapValue = mapValue == null ? null : new ObjectMapper().writeValueAsString(mapValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 创建流程操作记录
        FlowLogDO flowLogDO = new FlowLogDO()
                .setDelegationId(ProjectId)
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

    public List<List<Integer>> findAllSolution(List<List<Integer>> board) {
        List<List<Integer>> result = new ArrayList<>();
        if (board == null || board.size() != 9 || board.get(0).size() != 9) {
            return result;
        }
        findSolution(board, 0, 0, result);
        return result;
    }

    public void findSolution(List<List<Integer>> board, int row, int col, List<List<Integer>> result) {
        if (row == 9) {
            result.add(new ArrayList(board));
            return;
        }
        if (col == 9) {
            findSolution(board, row + 1, 0, result);
            return;
        }
        if (board.get(row).get(col) != 0) {
            findSolution(board, row, col + 1, result);
            return;
        }
        for (int i = 1; i <= 9; i++) {
            if (isValid(board, row, col, i)) {
                board.get(row).set(col, i);
                findSolution(board, row, col + 1, result);
                board.get(row).set(col, 0);
            }
        }
    }

    public boolean isValid(List<List<Integer>> board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row).get(i) == num) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (board.get(i).get(col) == num) {
                return false;
            }
        }
        int boxRow = row / 3;
        int boxCol = col / 3;
        for (int i = boxRow * 3; i < boxRow * 3 + 3; i++) {
            for (int j = boxCol * 3; j < boxCol * 3 + 3; j++) {
                if (board.get(i).get(j) == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasSolution(List<List<Integer>> board) {
        return findAllSolution(board).size() > 0;
    }

    public void printBoard(List<List<Integer>> board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> generateSudoku() {
        List<List<Integer>> board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(0);
            }
            board.add(row);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = (int) (Math.random() * 9 + 1);
                while (!isValid(board, i, j, num)) {
                    num = (int) (Math.random() * 9 + 1);
                }
                board.get(i).set(j, num);
            }
        }
        return board;
    }
}
