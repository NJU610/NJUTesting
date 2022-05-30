package cn.iocoder.yudao.module.system.service.delegation;



import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowLogService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.system.service.user.UserServiceImplTest;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;

import java.util.Date;

import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


@Import(DelegationServiceImpl.class)
class DelegationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DelegationServiceImpl delegationService;

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
    public void creatDelegation() {

        Mockito.when(tableMongoRepository.create("table12", null)).thenReturn(randomString());
        // 准备参数
        DelegationCreateReqVO createReqVO = randomPojo(DelegationCreateReqVO.class, o -> {
            o.setName(randomString());
        });

       Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        //获得新建委托编号
        Long delegationId = delegationService.createDelegation(createReqVO);
        assertNotNull(delegationId);

        //从数据库中获得委托
        DelegationDO delegationDO = delegationMapper.selectById(delegationId);
        assertPojoEquals(delegationDO,createReqVO);
    }

    @Test
    public void updateDelegation(){
        // 准备参数
        Mockito.when(tableMongoRepository.create("table12", null)).thenReturn(randomString());

        String newName = randomString();
        DelegationCreateReqVO createReqVO = randomPojo(DelegationCreateReqVO.class, o -> {
            o.setName(randomString());
        });
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());
        Long delegationId = delegationService.createDelegation(createReqVO);

        DelegationUpdateReqVO updateReqVO= randomPojo(DelegationUpdateReqVO.class, o -> {
            o.setName(newName);
            o.setUrl(randomString());
            o.setId(delegationId);
        });

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        //修改委托
        delegationService.updateDelegation(updateReqVO);

        //从数据库中获得委托
        DelegationDO delegationDO = delegationMapper.selectOne("name",newName );
        assertPojoEquals(delegationDO,updateReqVO);
    }

    @Test
    public void submitDelegation() {
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.DELEGATE_WRITING.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);
        DelegationSubmitReqVO submitReqVO = randomPojo(DelegationSubmitReqVO.class,o->{
            o.setId(1L);
        });

        delegationService.submitDelegation(submitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState());
    }

    @Test
    public void saveDelegationTable2() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationSaveTableReqVO updateReqVO = randomPojo(DelegationSaveTableReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.saveDelegationTable2(updateReqVO);

    }

    @Test
    public void saveDelegationTable3() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationSaveTableReqVO updateReqVO = randomPojo(DelegationSaveTableReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.saveDelegationTable3(updateReqVO);

    }

    @Test
    public void saveDelegationTable14() {

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationSaveTableReqVO updateReqVO = randomPojo(DelegationSaveTableReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.saveDelegationTable14(updateReqVO);

    }

    @Test
    public void distributeDelegation2Mkt(){

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationDistributeReqVO distributeReqVO = randomPojo(DelegationDistributeReqVO.class,o->{
            o.setId(1L);
            o.setAcceptorId(randomLongId());
        });

        delegationService.distributeDelegation2Mkt(distributeReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState());

    }

    @Test
    public void distributeDelegation2Test(){

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationDistributeReqVO distributeReqVO = randomPojo(DelegationDistributeReqVO.class,o->{
            o.setId(1L);
            o.setAcceptorId(randomLongId());
        });

        delegationService.distributeDelegation2Test(distributeReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState());
    }

    @Test
    public void auditDelegationSuccessMkt(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum. MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationAuditReqVO auditReqVO = randomPojo(DelegationAuditReqVO.class,o->{
            o.setId(1L);
            o.setRemark(randomString());
        });

        delegationService.auditDelegationSuccessMkt(auditReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION.getState());
    }

    @Test
    public void auditDelegationSuccessTest(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum. TESTING_DEPARTMENT_AUDIT_DELEGATION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationAuditReqVO auditReqVO = randomPojo(DelegationAuditReqVO.class,o->{
            o.setId(1L);
            o.setRemark(randomString());
        });

        delegationService.auditDelegationSuccessTest(auditReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState());

    }

    @Test
    public void auditDelegationFailMkt(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum. MARKETING_DEPARTMENT_AUDIT_DELEGATION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationAuditReqVO auditReqVO = randomPojo(DelegationAuditReqVO.class,o->{
            o.setId(1L);
            o.setRemark(randomString());
        });

        delegationService.auditDelegationFailMkt(auditReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
    }
    
    @Test
    public void auditDelegationFailTest(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum. TESTING_DEPARTMENT_AUDIT_DELEGATION.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationAuditReqVO auditReqVO = randomPojo(DelegationAuditReqVO.class,o->{
            o.setId(1L);
            o.setRemark(randomString());
        });

        delegationService.auditDelegationFailTest(auditReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL.getState());
    }

    @Test
    public void saveOffer(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum. MARKETING_DEPARTMENT_GENERATE_OFFER.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .offerId(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationSaveTableReqVO offerSaveReqVO = randomPojo(DelegationSaveTableReqVO.class,o->{
           o.setDelegationId(1L);
        });

        delegationService.saveOffer(offerSaveReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState());
    }

    @Test
    public void submitOffer(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_OFFER.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .offerId(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        OfferSubmitReqVO offerSubmitReqVO = randomPojo(OfferSubmitReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.submitOffer(offerSubmitReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_DEALING_OFFER.getState());
    }

    @Test
    public void rejectOffer(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_DEALING_OFFER.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .offerId(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        OfferRejectReqVO offerRejectReqVO = randomPojo(OfferRejectReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.rejectOffer(offerRejectReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.CLIENT_REJECT_OFFER.getState());
    }

    @Test
    public void acceptOffer(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_DEALING_OFFER.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .offerId(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        OfferAcceptReqVO offerAcceptReqVO = randomPojo(OfferAcceptReqVO.class,o->{
            o.setDelegationId(1L);
        });

        delegationService.acceptOffer(offerAcceptReqVO);

        DelegationDO delegationDO = delegationMapper.selectById(1L);

        assertEquals(delegationDO.getState(),DelegationStateEnum.MARKETING_DEPARTMENT_GENERATE_CONTRACT.getState());
    }

    @Test
    public void deleteDelegation(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.CLIENT_DEALING_OFFER.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .table14Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .offerId(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        delegationService.deleteDelegation(1L);

        assertEquals(delegationMapper.selectCount(),0L);
    }


    @Test
    public void cancelDelegationClient(){

        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationCancelReqVO delegationCancelReqVO = randomPojo(DelegationCancelReqVO.class,o->{
           o.setId(1L);
        });

        delegationService.cancelDelegationClient(delegationCancelReqVO);
    }

    @Test
    public void cancelDelegationAdmin(){
        Mockito.when(userService.getUser(any())).thenReturn(new AdminUserDO());

        DelegationDO del = DelegationDO.builder()
                .id(1L)
                .state(DelegationStateEnum.WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF.getState())
                .table2Id(randomString())
                .table3Id(randomString())
                .launchTime(new Date())
                .name(randomString())
                .build();

        del.setCreateTime(new Date());
        del.setUpdateTime(new Date());
        del.setDeleted(false);

        delegationMapper.insert(del);

        DelegationCancelReqVO delegationCancelReqVO = randomPojo(DelegationCancelReqVO.class,o->{
            o.setId(1L);
        });

        delegationService.cancelDelegationAdmin(delegationCancelReqVO);
    }


}