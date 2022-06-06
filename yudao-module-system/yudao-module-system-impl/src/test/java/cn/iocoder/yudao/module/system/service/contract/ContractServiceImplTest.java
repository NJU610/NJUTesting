package cn.iocoder.yudao.module.system.service.contract;

import cn.hutool.core.lang.UUID;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
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

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@Import(ContractServiceImpl.class)
class ContractServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ContractServiceImpl contractService;

    @Resource
    private ContractMapper contractMapper;

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
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void createContract() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());


        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);
        ContractCreateReqVO createReqVO = randomPojo(ContractCreateReqVO.class, o->{
            o.setDelegationId(delegationId);
        });

        contractService.createContract(createReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());



    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveContractTable4() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSaveTableReqVO saveReqVO = randomPojo(ContractSaveTableReqVO.class, o->{
            o.setContractId(contractId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());

        contractService.saveContractTable4(saveReqVO);

        assertNotNull(contractMapper.selectById(contractId).getTable4Id());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void saveContractTable5() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSaveTableReqVO saveReqVO = randomPojo(ContractSaveTableReqVO.class, o->{
            o.setContractId(contractId);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());

        contractService.saveContractTable5(saveReqVO);

        assertNotNull(contractMapper.selectById(contractId).getTable5Id());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void submitContractStaff() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSubmitReqVO submitReqVO = randomPojo(ContractSubmitReqVO.class, o->{
           o.setId(contractId);
        });

        contractService.submitContractStaff(submitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void submitContractClient() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSubmitReqVO submitReqVO = randomPojo(ContractSubmitReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.submitContractClient(submitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void acceptContractClient() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractAcceptReqVO acceptReqVO = randomPojo(ContractAcceptReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.acceptContractClient(acceptReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState());


    }

    @Test
    @JunitPerfConfig(threads = 8 ,warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void rejectContractClient() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractRejectReqVO rejectReqVO = randomPojo(ContractRejectReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.rejectContractClient(rejectReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL.getState());


    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void rejectContractStaff() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractRejectReqVO rejectReqVO = randomPojo(ContractRejectReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.rejectContractStaff(rejectReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void acceptContractStaff() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractAcceptReqVO acceptReqVO = randomPojo(ContractAcceptReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.acceptContractStaff(acceptReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CONTRACT_SIGNING.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void uploadDocument() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(delegationId)
                .state(DelegationStateEnum.CONTRACT_SIGNING.getState())
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

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractUploadDocReqVO uploadReqVO = randomPojo(ContractUploadDocReqVO.class, o->{
            o.setId(contractId);
        });

        contractService.uploadDocument(uploadReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(delegationId);

        assertEquals(delegationDO.getState(),DelegationStateEnum.WAITING_TESTING_DEPT_MANAGER_FILL_PROJECT_ID.getState());

    }

    @Test
    @JunitPerfConfig(threads = 8, warmUp = 0, duration = 1000,reporter = {HtmlReporter.class})
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    void deleteContract() {

        long delegationId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long contractId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        ContractDO con = ContractDO.builder()
                .id(contractId)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        contractService.deleteContract(contractId);

        assertEquals(contractMapper.selectCount(),0L);

    }
}
