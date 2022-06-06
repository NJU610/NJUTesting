package cn.iocoder.yudao.module.system.service.solution;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.ContractSubmitReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionSaveTableReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionSubmitReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.solution.SolutionMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Import(SolutionServiceImpl.class)
class SolutionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private SolutionServiceImpl solutionService;

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
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionCreateReqVO createReqVO = randomPojo(SolutionCreateReqVO.class, o->{
            o.setDelegationId(delegationId);
        });

       solutionService.createSolution(createReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(solutionMapper.selectCount(),1);
        assertNotNull(delegationDO.getSolutionId());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveSolutionTable6() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionSaveTableReqVO saveReqVO = randomPojo(SolutionSaveTableReqVO.class, o->{
            o.setSolutionId(solutionId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        solutionService.saveSolutionTable6(saveReqVO);

        assertNotNull(solutionMapper.selectById(solutionId).getTable6Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveSolutionTable13() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;


        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionSaveTableReqVO saveReqVO = randomPojo(SolutionSaveTableReqVO.class, o->{
            o.setSolutionId(solutionId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        solutionService.saveSolutionTable13(saveReqVO);

        assertNotNull(solutionMapper.selectById(solutionId).getTable13Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void submitSolutionTable6() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;


        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .table6Id(randomString())
                .table13Id(randomString())
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionSubmitReqVO submitReqVO = randomPojo(SolutionSubmitReqVO.class, o->{
            o.setSolutionId(solutionId);
        });

        solutionService.submitSolutionTable6(submitReqVO);

        assertEquals(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION.getState(),delegationMapper.selectById(delegationId).getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void auditSuccess() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .table6Id(randomString())
                .table13Id(randomString())
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionSubmitReqVO submitReqVO = randomPojo(SolutionSubmitReqVO.class, o->{
            o.setSolutionId(solutionId);
        });

        solutionService.auditSuccess(submitReqVO);

        assertEquals(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState(),delegationMapper.selectById(delegationId).getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void auditFail() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .table6Id(randomString())
                .table13Id(randomString())
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionSubmitReqVO submitReqVO = randomPojo(SolutionSubmitReqVO.class, o->{
            o.setSolutionId(solutionId);
        });

        solutionService.auditFail(submitReqVO);

        assertEquals(DelegationStateEnum.QUALITY_DEPT_AUDIT_TEST_SOLUTION_FAIL.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getSolutionTable6() {

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getSolutionTable13() {
    }

    @Test
    void updateSolution() {
//        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
//        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
//        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SolutionDO sol = SolutionDO.builder()
                .id(1L)
                .auditorId(1L)
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);


        SolutionUpdateReqVO updateReqVO = randomPojo(SolutionUpdateReqVO.class, o->{
           o.setId(1L);
           o.setAuditorId(2L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        solutionService.updateSolution(updateReqVO);

        assertEquals(2,solutionMapper.selectById(1L).getAuditorId());


    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void deleteSolution() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        SolutionDO sol = SolutionDO.builder()
                .id(solutionId)
                .auditorId(1L)
                .build();

        sol.setUpdateTime(new Date());
        sol.setCreateTime(new Date());
        sol.setDeleted(false);

        solutionMapper.insert(sol);

        solutionService.deleteSolution(solutionId);
        assertEquals(0,solutionMapper.selectCount());
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