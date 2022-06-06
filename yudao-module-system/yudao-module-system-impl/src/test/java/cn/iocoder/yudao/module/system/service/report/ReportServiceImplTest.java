package cn.iocoder.yudao.module.system.service.report;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.SolutionSaveTableReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.report.ReportMapper;
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

import java.util.Date;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Import(ReportServiceImpl.class)
class ReportServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ReportServiceImpl reportService;

    @Resource
    private ReportMapper reportMapper;

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
    void createReport() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
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

        ReportCreateReqVO createReqVO = randomPojo(ReportCreateReqVO.class, o->{
            o.setDelegationId(1L);
        });

       reportService.createReport(createReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertNotNull(delegationDO.getReportId());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveReportTable7() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

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
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(reportId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable7(saveReqVO);


        assertNotNull(reportMapper.selectById(reportId).getTable7Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveReportTable8() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
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

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(reportId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable8(saveReqVO);


        assertNotNull(reportMapper.selectById(reportId).getTable8Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveReportTable9() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
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

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(reportId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable9(saveReqVO);


        assertNotNull(reportMapper.selectById(reportId).getTable9Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveReportTable10() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
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

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(reportId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable10(saveReqVO);


        assertNotNull(reportMapper.selectById(reportId).getTable10Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveReportTable11() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
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

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(reportId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable11(saveReqVO);


        assertNotNull(reportMapper.selectById(reportId).getTable11Id());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void submitReport() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSubmitReqVO submitReqVO = randomPojo(ReportSubmitReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.submitReport(submitReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState(),delegationMapper.selectById(delegationId).getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void acceptReportManager() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportAcceptReqVO acceptReqVO = randomPojo(ReportAcceptReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.acceptReportManager(acceptReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void rejectReportManager() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportRejectReqVO rejectReqVO = randomPojo(ReportRejectReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.rejectReportManager(rejectReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void acceptReportClient() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportAcceptReqVO acceptReqVO = randomPojo(ReportAcceptReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.acceptReportClient(acceptReqVO);


        assertEquals(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void rejectReportClient() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportRejectReqVO rejectReqVO= randomPojo(ReportRejectReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.rejectReportClient(rejectReqVO);


        assertEquals(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void acceptReportSignatory() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportAcceptReqVO acceptReqVO= randomPojo(ReportAcceptReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.acceptReportSignatory(acceptReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void rejectReportSignatory() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportRejectReqVO rejectReqVO = randomPojo(ReportRejectReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.rejectReportSignatory(rejectReqVO);


        assertEquals(DelegationStateEnum.SIGNATORY_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void archiveReport() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportArchiveReqVO archiveReqVO = randomPojo(ReportArchiveReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.archiveReport(archiveReqVO);


        assertEquals(DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void sendReport() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long solutionId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long reportId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .solutionId(solutionId)
                .reportId(reportId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(reportId)
                .table7Id(randomString())
                .table8Id(randomString())
                .table9Id(randomString())
                .table10Id(randomString())
                .table11Id(randomString())
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSendReqVO sendReqVO = randomPojo(ReportSendReqVO.class, o->{
            o.setId(reportId);
        });

        reportService.sendReport(sendReqVO);


        assertEquals(DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT.getState(),delegationMapper.selectById(delegationId).getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void receiveReport() {
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void deleteReport() {
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getReport() {
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getReportTable() {
    }

    @Test
    void getReportList() {
    }

    @Test
    void getReportPage() {
    }

    @Test
    void testGetReportList() {
    }
}