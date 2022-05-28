package cn.iocoder.yudao.module.system.service.report;

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
    void saveReportTable7() {
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
                .reportId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(1L)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable7(saveReqVO);


        assertNotNull(reportMapper.selectById(1).getTable7Id());
    }

    @Test
    void saveReportTable8() {
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

        ReportDO rep = ReportDO.builder()
                .id(1L)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable8(saveReqVO);


        assertNotNull(reportMapper.selectById(1).getTable8Id());
    }

    @Test
    void saveReportTable9() {
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

        ReportDO rep = ReportDO.builder()
                .id(1L)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable9(saveReqVO);


        assertNotNull(reportMapper.selectById(1).getTable9Id());
    }

    @Test
    void saveReportTable10() {
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

        ReportDO rep = ReportDO.builder()
                .id(1L)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable10(saveReqVO);


        assertNotNull(reportMapper.selectById(1).getTable10Id());
    }

    @Test
    void saveReportTable11() {
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

        ReportDO rep = ReportDO.builder()
                .id(1L)
                .build();

        rep.setUpdateTime(new Date());
        rep.setCreateTime(new Date());
        rep.setDeleted(false);

        reportMapper.insert(rep);

        ReportSaveTableReqVO saveReqVO = randomPojo(ReportSaveTableReqVO.class, o->{
            o.setReportId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());
        reportService.saveReportTable11(saveReqVO);


        assertNotNull(reportMapper.selectById(1).getTable11Id());
    }

    @Test
    void submitReport() {
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
                .reportId(2L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(2L)
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
            o.setId(2L);
        });

        reportService.submitReport(submitReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState(),delegationMapper.selectById(1).getState());

    }

    @Test
    void acceptReportManager() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(3L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(3L)
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
            o.setId(3L);
        });

        reportService.acceptReportManager(acceptReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void rejectReportManager() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(4L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(4L)
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
            o.setId(4L);
        });

        reportService.rejectReportManager(rejectReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void acceptReportClient() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(5L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(5L)
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
            o.setId(5L);
        });

        reportService.acceptReportClient(acceptReqVO);


        assertEquals(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void rejectReportClient() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(6L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(6L)
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
            o.setId(6L);
        });

        reportService.rejectReportClient(rejectReqVO);


        assertEquals(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void acceptReportSignatory() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(7L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(7L)
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
            o.setId(7L);
        });

        reportService.acceptReportSignatory(acceptReqVO);


        assertEquals(DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void rejectReportSignatory() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(8L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(8L)
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
            o.setId(8L);
        });

        reportService.rejectReportSignatory(rejectReqVO);


        assertEquals(DelegationStateEnum.SIGNATORY_AUDIT_TEST_REPORT_FAIL.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void archiveReport() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(9L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(9L)
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
            o.setId(9L);
        });

        reportService.archiveReport(archiveReqVO);


        assertEquals(DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void sendReport() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .solutionId(1L)
                .reportId(10L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        ReportDO rep = ReportDO.builder()
                .id(10L)
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
            o.setId(10L);
        });

        reportService.sendReport(sendReqVO);


        assertEquals(DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT.getState(),delegationMapper.selectById(1).getState());
    }

    @Test
    void receiveReport() {
    }

    @Test
    void deleteReport() {
    }

    @Test
    void getReport() {
    }

    @Test
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