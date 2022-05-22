package cn.iocoder.yudao.module.system.service.contract;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.DelegationSubmitReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.contract.ContractMapper;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.delegation.DelegationServiceImpl;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

import java.util.Date;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
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
    void createContract() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
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
            o.setDelegationId(1L);
        });

        contractService.createContract(createReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());
    }

    @Test
    void saveContractTable4() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSaveTableReqVO saveReqVO = randomPojo(ContractSaveTableReqVO.class, o->{
            o.setContractId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());

        contractService.saveContractTable4(saveReqVO);

        assertNotNull(contractMapper.selectById(1).getTable4Id());
    }

    @Test
    void saveContractTable5() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSaveTableReqVO saveReqVO = randomPojo(ContractSaveTableReqVO.class, o->{
            o.setContractId(1L);
        });

        Mockito.when(tableMongoRepository.create(any(),any())).thenReturn(randomString());

        contractService.saveContractTable5(saveReqVO);

        assertNotNull(contractMapper.selectById(1).getTable5Id());
    }

    @Test
    void submitContractStaff() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSubmitReqVO submitReqVO = randomPojo(ContractSubmitReqVO.class, o->{
           o.setId(1L);
        });

        contractService.submitContractStaff(submitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState());
    }

    @Test
    void submitContractClient() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractSubmitReqVO submitReqVO = randomPojo(ContractSubmitReqVO.class, o->{
            o.setId(1L);
        });

        contractService.submitContractClient(submitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState());

    }

    @Test
    void acceptContractClient() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractAcceptReqVO acceptReqVO = randomPojo(ContractAcceptReqVO.class, o->{
            o.setId(1L);
        });

        contractService.acceptContractClient(acceptReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_WRITING_CONTRACT.getState());
    }

    @Test
    void rejectContractClient() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractRejectReqVO rejectReqVO = randomPojo(ContractRejectReqVO.class, o->{
            o.setId(1L);
        });

        contractService.rejectContractClient(rejectReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_AUDIT_CONTRACT_FAIL.getState());
    }

    @Test
    void rejectContractStaff() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractRejectReqVO rejectReqVO = randomPojo(ContractRejectReqVO.class, o->{
            o.setId(1L);
        });

        contractService.rejectContractStaff(rejectReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL.getState());

    }

    @Test
    void acceptContractStaff() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_CONTRACT.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractAcceptReqVO acceptReqVO = randomPojo(ContractAcceptReqVO.class, o->{
            o.setId(1L);
        });

        contractService.acceptContractStaff(acceptReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CONTRACT_SIGNING.getState());

    }

    @Test
    void uploadDocument() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CONTRACT_SIGNING.getState())
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

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        ContractUploadDocReqVO uploadReqVO = randomPojo(ContractUploadDocReqVO.class, o->{
            o.setId(1L);
        });

        contractService.uploadDocument(uploadReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_UPLOAD_SAMPLE_INFO.getState());
    }

    @Test
    void deleteContract() {

        ContractDO con = ContractDO.builder()
                .id(1L)
                .table4Id(randomString())
                .table5Id(randomString())
                .build();

        con.setUpdateTime(new Date());
        con.setCreateTime(new Date());
        con.setDeleted(false);

        contractMapper.insert(con);

        contractService.deleteContract(1L);

        assertEquals(contractMapper.selectCount(),0L);
    }
}