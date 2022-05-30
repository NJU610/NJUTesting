package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowLogMapper;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@Import(FlowLogServiceImpl.class)
class FlowLogServiceImplTest {

    @Resource
    private FlowLogService flowLogService;

    @MockBean
    private FlowLogMapper flowLogMapper;

    @MockBean
    private DelegationService delegationService;

    @MockBean
    private ContractService contractService;

    @MockBean
    private AdminUserService userService;

    @Test
    void saveLog() {
    }

    @Test
    void listLogs() {
    }

    @Test
    void createFlowLog() {
    }

    @Test
    void updateFlowLog() {
    }

    @Test
    void deleteFlowLog() {
    }

    @Test
    void getFlowLog() {
    }

    @Test
    void getFlowLogList() {
    }

    @Test
    void getFlowLogPage() {
    }

    @Test
    void testGetFlowLogList() {
    }
}