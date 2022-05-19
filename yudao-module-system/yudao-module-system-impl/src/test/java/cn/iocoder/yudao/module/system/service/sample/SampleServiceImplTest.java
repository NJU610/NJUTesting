package cn.iocoder.yudao.module.system.service.sample;

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
    void createSample() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleCreateReqVO createReqVO = randomPojo(SampleCreateReqVO.class, o->{
            o.setDelegationId(1L);
        });

        samplesService.createSample(createReqVO);

        assertNotNull(delegationMapper.selectById(1).getSampleId());
    }

    @Test
    void updateSample() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .sampleId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.UNSENT.getState())
                .build();

        sampleMapper.insert(sam);

        String randomString = randomString();
        SampleUpdateReqVO updateReqVO = randomPojo(SampleUpdateReqVO.class, o->{
            o.setId(1L);
            o.setInformation(randomString);
        });

        samplesService.updateSample(updateReqVO);

        assertEquals(sampleMapper.selectById(1).getInformation(),randomString);
    }

    @Test
    void submitSample() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .sampleId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.UNSENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleSubmitReqVO submitReqVO = randomPojo(SampleSubmitReqVO.class, o->{
            o.setId(1L);
        });

        samplesService.submitSample(submitReqVO);

        assertEquals(delegationMapper.selectById(1).getState(),DelegationStateEnum.CHECKING_SAMPLE.getState());
        assertEquals(sampleMapper.selectById(1).getState(),SampleStateEnum.SENT.getState());
    }

    @Test
    void auditSampleSuccess() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CHECKING_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .sampleId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleAuditReqVO auditReqVO = randomPojo(SampleAuditReqVO.class, o->{
            o.setId(1L);
        });

        samplesService.auditSampleSuccess(auditReqVO);

        assertEquals(delegationMapper.selectById(1).getState(),DelegationStateEnum.TESTING_DEPT_WRITING_TEST_SOLUTION.getState());

    }

    @Test
    void auditSampleFail() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CHECKING_SAMPLE.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .contractId(1L)
                .sampleId(1L)
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        SampleAuditReqVO auditReqVO = randomPojo(SampleAuditReqVO.class, o->{
            o.setId(1L);
        });

        samplesService.auditSampleFail(auditReqVO);

        assertEquals(delegationMapper.selectById(1).getState(),DelegationStateEnum.SAMPLE_CHECK_FAIL_MODIFY_SAMPLE.getState());
    }

    @Test
    void deleteSample() {
        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sampleMapper.insert(sam);

        samplesService.deleteSample(1L);

        assertEquals(sampleMapper.selectCount(),0);
    }

    @Test
    void getSample() {
        SampleDO sam = SampleDO.builder()
                .id(1L)
                .state(SampleStateEnum.SENT.getState())
                .build();

        sam.setUpdateTime(new Date());
        sam.setCreateTime(new Date());
        sam.setDeleted(false);

        sampleMapper.insert(sam);

        SampleDO getSam = samplesService.getSample(1L) ;
        assertEquals(getSam,sam);
    }

    @Test
    void getSampleList() {
    }

    @Test
    void getSamplePage() {
    }

    @Test
    void testGetSampleList() {
    }
}