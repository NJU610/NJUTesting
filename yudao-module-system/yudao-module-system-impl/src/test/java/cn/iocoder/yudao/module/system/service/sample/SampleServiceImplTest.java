package cn.iocoder.yudao.module.system.service.sample;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.ContractSaveTableReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.SampleAuditReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.SampleCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.SampleSubmitReqVO;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.SampleUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.dal.mysql.sample.SampleMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.enums.sample.SampleStateEnum;
import cn.iocoder.yudao.module.system.service.contract.ContractServiceImpl;
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

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Import(SampleServiceImpl.class)
class SampleServiceImplTest extends BaseDbUnitTest {

    @Resource
    private SampleServiceImpl samplesService;

    @Resource
    private SampleMapper sampleMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @Lazy
    @MockBean
    private FlowLogService flowLogService;

    @MockBean
    private AdminUserService userService;

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void createSample() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
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

        SampleCreateReqVO createReqVO = randomPojo(SampleCreateReqVO.class, o->{
            o.setDelegationId(delegationId);
        });

        samplesService.createSample(createReqVO);

        assertNotNull(delegationMapper.selectById(delegationId).getSampleId());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void updateSample() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .sampleId(sampleId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.UNSENT.getState())
                .build();

        sampleMapper.insert(sam);

        String randomString = randomString();
        SampleUpdateReqVO updateReqVO = randomPojo(SampleUpdateReqVO.class, o->{
            o.setId(sampleId);
            o.setInformation(randomString);
        });

        samplesService.updateSample(updateReqVO);

        assertEquals(sampleMapper.selectById(sampleId).getInformation(),randomString);
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void submitSample() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .sampleId(sampleId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.UNSENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleSubmitReqVO submitReqVO = randomPojo(SampleSubmitReqVO.class, o->{
            o.setId(sampleId);
        });

        samplesService.submitSample(submitReqVO);

        assertEquals(delegationMapper.selectById(delegationId).getState(),DelegationStateEnum.CHECKING_SAMPLE.getState());
        assertEquals(sampleMapper.selectById(sampleId).getState(),SampleStateEnum.SENT.getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void auditSampleSuccess() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CHECKING_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .sampleId(sampleId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleAuditReqVO auditReqVO = randomPojo(SampleAuditReqVO.class, o->{
            o.setId(sampleId);
        });

        samplesService.auditSampleSuccess(auditReqVO);

        assertEquals(delegationMapper.selectById(delegationId).getState(),DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void auditSampleFail() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CHECKING_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(contractId)
                .sampleId(sampleId)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleAuditReqVO auditReqVO = randomPojo(SampleAuditReqVO.class, o->{
            o.setId(sampleId);
        });

        samplesService.auditSampleFail(auditReqVO);

        assertEquals(delegationMapper.selectById(delegationId).getState(),DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE.getState());
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void deleteSample() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        samplesService.deleteSample(sampleId);

        assertEquals(sampleMapper.selectCount(),0);
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getSample() {
        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long sampleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        SampleDO sam = SampleDO.builder()
                .id(sampleId)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sam.setUpdateTime(new Date());
        sam.setCreateTime(new Date());
        sam.setDeleted(false);

        sampleMapper.insert(sam);

        SampleDO getSam = samplesService.getSample(sampleId) ;
        assertEquals(getSam,sam);
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getSampleList() {
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void getSamplePage() {
    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void testGetSampleList() {
    }
}