package cn.iocoder.yudao.module.system.service.flow;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.flow.FlowLogMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractService;
import cn.iocoder.yudao.module.system.service.delegation.DelegationService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;

@Import(FlowLogServiceImpl.class)
class FlowLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FlowLogService flowLogService;

    @Resource
    private FlowLogMapper flowLogMapper;

    @MockBean
    private DelegationService delegationService;

    @MockBean
    private ContractService contractService;

    @MockBean
    private AdminUserService userService;

    @Test
    void saveLog() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});

        assertEquals(flowLogMapper.selectCount(),1);

    }

    @Test
    void saveLogByProject() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        flowLogService.saveLogByProject(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"nju_test",
                new HashMap<String, Object>(){{put("project", del);}});

        assertEquals(flowLogMapper.selectCount(),1);

    }

    @Test
    void listLogs() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});

        List<FlowLogDO> flow = flowLogService.listLogs(1L);

        assertEquals(flow.get(0).getRemark(),"wulala");
    }

    @Test
    void createFlowLog() {

        FlowLogCreateReqVO createReqVO = randomPojo(FlowLogCreateReqVO.class,o->{
            o.setDelegationId(1L);
            o.setRemark("haha");
        });

        Long id = flowLogService.createFlowLog(createReqVO);

        assertEquals(flowLogMapper.selectById(id).getRemark(),"haha");

    }

    @Test
    void updateFlowLog() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        Long id = flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});


        FlowLogUpdateReqVO updateReqVO = randomPojo(FlowLogUpdateReqVO.class,o->{
            o.setId(id);
            o.setRemark("xiugai");
        });

        flowLogService.updateFlowLog(updateReqVO);

        assertEquals(flowLogMapper.selectById(id).getRemark(),"xiugai");
    }

    @Test
    void deleteFlowLog() {
        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        Long id = flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});

        flowLogService.deleteFlowLog(id);

        assertEquals(0,flowLogMapper.selectCount());
    }

    @Test
    void getFlowLog() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        Long id = flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});

        assertNotNull(flowLogService.getFlowLog(id));
    }

    @Test
    void getFlowLogList() {

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .build();

        Long id = flowLogService.saveLog(1L,1L, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION,"wulala",
                new HashMap<String, Object>(){{put("delegation", del);}});

        assertNotNull(flowLogService.getFlowLogList(new ArrayList<Long>(){{add(id);}}));

    }

    @Test
    void getFlowLogPage() {

        FlowLogPageReqVO pageReqVO = randomPojo(FlowLogPageReqVO.class,o->{
            o.setDelegationId(1L);
        });

        flowLogService.getFlowLogPage(pageReqVO);
    }

    @Test
    void testGetFlowLogList() {

        FlowLogExportReqVO exportReqVO = randomPojo(FlowLogExportReqVO.class,o->{
            o.setDelegationId(1L);
        });

        flowLogService.getFlowLogList(exportReqVO);
    }
}