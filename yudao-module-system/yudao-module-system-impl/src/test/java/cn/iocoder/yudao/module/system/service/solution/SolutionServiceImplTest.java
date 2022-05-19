package cn.iocoder.yudao.module.system.service.solution;

import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.solution.SolutionMapper;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@Import(SolutionServiceImpl.class)
class SolutionServiceImplTest {

    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @MockBean
    private TableMongoRepository tableMongoRepository;

    @MockBean
    @Lazy
    private FlowLogService flowLogService;

    @MockBean
    private AdminUserService userService;

    @Test
    void createSolution() {

    }

    @Test
    void saveSolutionTable6() {
    }

    @Test
    void saveSolutionTable13() {
    }

    @Test
    void submitSolutionTable6() {
    }

    @Test
    void auditSuccess() {
    }

    @Test
    void auditFail() {
    }

    @Test
    void getSolutionTable6() {
    }

    @Test
    void getSolutionTable13() {
    }

    @Test
    void updateSolution() {
    }

    @Test
    void deleteSolution() {
    }

    @Test
    void getSolution() {
    }

    @Test
    void getSolutionList() {
    }

    @Test
    void getSolutionPage() {
    }

    @Test
    void testGetSolutionList() {
    }
}