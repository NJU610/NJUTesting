package cn.iocoder.yudao.module.system.service.company;//package cn.iocoder.yudao.module.system.service.userCompany;
//
//import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyCreateReqVO;
//import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyExportReqVO;
//import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyPageReqVO;
//import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyUpdateReqVO;
//import cn.iocoder.yudao.module.system.service.company.UserCompanyServiceImpl;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import javax.annotation.Resource;
//
//import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
//
//import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
//import cn.iocoder.yudao.module.system.dal.mysql.company.UserCompanyMapper;
//import cn.iocoder.yudao.framework.common.pojo.PageResult;
//
//import org.springframework.context.annotation.Import;
//import java.util.*;
//
//import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
//import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
//import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
//* {@link UserCompanyServiceImpl} 的单元测试类
//*
//* @author qjy
//*/
//@Import(UserCompanyServiceImpl.class)
//public class UserCompanyServiceImplTest extends BaseDbUnitTest {
//
//    @Resource
//    private UserCompanyServiceImpl userCompanyService;
//
//    @Resource
//    private UserCompanyMapper userCompanyMapper;
//
//    @Test
//    public void testCreateUserCompany_success() {
//        // 准备参数
//        UserCompanyCreateReqVO reqVO = randomPojo(UserCompanyCreateReqVO.class);
//
//        // 调用
//        Long userCompanyId = userCompanyService.createUserCompany(reqVO);
//        // 断言
//        assertNotNull(userCompanyId);
//        // 校验记录的属性是否正确
//        UserCompanyDO userCompany = userCompanyMapper.selectById(userCompanyId);
//        assertPojoEquals(reqVO, userCompany);
//    }
//
//    @Test
//    public void testUpdateUserCompany_success() {
//        // mock 数据
//        UserCompanyDO dbUserCompany = randomPojo(UserCompanyDO.class);
//        userCompanyMapper.insert(dbUserCompany);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        UserCompanyUpdateReqVO reqVO = randomPojo(UserCompanyUpdateReqVO.class, o -> {
//            o.setId(dbUserCompany.getId()); // 设置更新的 ID
//        });
//
//        // 调用
//        userCompanyService.updateUserCompany(reqVO);
//        // 校验是否更新正确
//        UserCompanyDO userCompany = userCompanyMapper.selectById(reqVO.getId()); // 获取最新的
//        assertPojoEquals(reqVO, userCompany);
//    }
//
//    @Test
//    public void testUpdateUserCompany_notExists() {
//        // 准备参数
//        UserCompanyUpdateReqVO reqVO = randomPojo(UserCompanyUpdateReqVO.class);
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userCompanyService.updateUserCompany(reqVO), USER_COMPANY_NOT_EXISTS);
//    }
//
//    @Test
//    public void testDeleteUserCompany_success() {
//        // mock 数据
//        UserCompanyDO dbUserCompany = randomPojo(UserCompanyDO.class);
//        userCompanyMapper.insert(dbUserCompany);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Long id = dbUserCompany.getId();
//
//        // 调用
//        userCompanyService.deleteUserCompany(id);
//       // 校验数据不存在了
//       assertNull(userCompanyMapper.selectById(id));
//    }
//
//    @Test
//    public void testDeleteUserCompany_notExists() {
//        // 准备参数
//        Long id = randomLongId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> userCompanyService.deleteUserCompany(id), USER_COMPANY_NOT_EXISTS);
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetUserCompanyPage() {
//       // mock 数据
//       UserCompanyDO dbUserCompany = randomPojo(UserCompanyDO.class, o -> { // 等会查询到
//           o.setUserId(null);
//           o.setCompanyId(null);
//           o.setCreateTime(null);
//       });
//       userCompanyMapper.insert(dbUserCompany);
//       // 测试 userId 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setUserId(null)));
//       // 测试 companyId 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setCompanyId(null)));
//       // 测试 createTime 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setCreateTime(null)));
//       // 准备参数
//       UserCompanyPageReqVO reqVO = new UserCompanyPageReqVO();
//       reqVO.setUserId(null);
//       reqVO.setCompanyId(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       PageResult<UserCompanyDO> pageResult = userCompanyService.getUserCompanyPage(reqVO);
//       // 断言
//       assertEquals(1, pageResult.getTotal());
//       assertEquals(1, pageResult.getList().size());
//       assertPojoEquals(dbUserCompany, pageResult.getList().get(0));
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetUserCompanyList() {
//       // mock 数据
//       UserCompanyDO dbUserCompany = randomPojo(UserCompanyDO.class, o -> { // 等会查询到
//           o.setUserId(null);
//           o.setCompanyId(null);
//           o.setCreateTime(null);
//       });
//       userCompanyMapper.insert(dbUserCompany);
//       // 测试 userId 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setUserId(null)));
//       // 测试 companyId 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setCompanyId(null)));
//       // 测试 createTime 不匹配
//       userCompanyMapper.insert(cloneIgnoreId(dbUserCompany, o -> o.setCreateTime(null)));
//       // 准备参数
//       UserCompanyExportReqVO reqVO = new UserCompanyExportReqVO();
//       reqVO.setUserId(null);
//       reqVO.setCompanyId(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       List<UserCompanyDO> list = userCompanyService.getUserCompanyList(reqVO);
//       // 断言
//       assertEquals(1, list.size());
//       assertPojoEquals(dbUserCompany, list.get(0));
//    }
//
//}



import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyCreateByCodeReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;
import cn.iocoder.yudao.module.system.dal.mysql.company.UserCompanyMapper;
import cn.iocoder.yudao.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.yudao.module.system.service.company.UserCompanyServiceImpl;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

import java.util.Date;

import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;

@Import(UserCompanyServiceImpl.class)
class UserCompanyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private UserCompanyMapper userCompanyMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private UserCompanyServiceImpl userCompanyService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private PermissionService permissionService;

    @Test
    void createUserCompany() {

//        //构造插入数据库
//        UserCompanyDO use = UserCompanyDO.builder()
//                .id(1L)
//                .companyId(1L)
//                .userId(1L)
//                .build();
//
//        userCompanyMapper.insert(use);


        //要注意一些数据库中必须存在的量
        CompanyDO com = CompanyDO.builder()
                .id(1L)
                .name("小公司")
                .address(randomString())
                .phone(randomString())
                .code("swg")
                .build();

        companyMapper.insert(com);

        //观察assignCustomerRole得知需要一个customer
        Mockito.when(roleService.getRoleByCode(RoleCodeEnum.CUSTOMER.getCode())).thenReturn(
                new RoleDO().setId(1L).setCode("customer").setName("客户")
        );

        //构造参数
        UserCompanyCreateReqVO createReqVO  = randomPojo(UserCompanyCreateReqVO.class, o->{
            o.setCompanyId(1L);
            o.setUserId(1L);
        });

        //运行该函数，简单检验返回值不为空
        assertNotNull(userCompanyService.createUserCompany(createReqVO));

    }

    @Test
    void updateUserCompany() {

        UserCompanyDO use = UserCompanyDO.builder()
                .id(1L)
                .companyId(1L)
                .userId(1L)
                .build();

        use.setCreateTime(new Date());
        use.setCreateTime(new Date());
        use.setDeleted(false);

        userCompanyMapper.insert(use);


        //插入另一个公司数据
        CompanyDO com = CompanyDO.builder()
                .id(2L)
                .name("大公司")
                .address(randomString())
                .phone(randomString())
                .code("swgnb")
                .build();

        companyMapper.insert(com);


        UserCompanyUpdateReqVO updateReqVO = randomPojo(UserCompanyUpdateReqVO.class,o->{
            o.setCompanyId(2L);
            o.setUserId(1L);
        });

        userCompanyService.updateUserCompany(updateReqVO);

        assertEquals(userCompanyMapper.selectByUser(1L).getCompanyId(),2);
    }

    @Test
    void deleteUserCompany() {

        Long count = userCompanyMapper.selectCount();

        UserCompanyDO use = UserCompanyDO.builder()
                .id(2L)
                .companyId(2L)
                .userId(2L)
                .build();

        use.setCreateTime(new Date());
        use.setCreateTime(new Date());
        use.setDeleted(false);

        userCompanyMapper.insert(use);

        Mockito.when(roleService.getRoleByCode(RoleCodeEnum.CUSTOMER.getCode())).thenReturn(
                new RoleDO().setId(1L).setCode("customer").setName("客户")
        );

        userCompanyService.deleteUserCompany(2L);

        assertEquals(count,userCompanyMapper.selectCount());
    }

    @Test
    void getUserCompany() {

    }

    @Test
    void getUserCompanyList() {
    }

    @Test
    void getUserCompanyPage() {
    }

    @Test
    void testGetUserCompanyList() {
    }

    @Test
    void createUserCompanyByCode() {

        CompanyDO com = CompanyDO.builder()
                .id(1L)
                .name("小公司")
                .address(randomString())
                .phone(randomString())
                .code("swg")
                .build();

        companyMapper.insert(com);


        UserCompanyCreateByCodeReqVO createReqVO = randomPojo(UserCompanyCreateByCodeReqVO.class, o->{
            o.setCode("swg");
            o.setUserId(1L);
        });

        Mockito.when(roleService.getRoleByCode(RoleCodeEnum.CUSTOMER.getCode())).thenReturn(
                new RoleDO().setId(1L).setCode("customer").setName("客户")
        );

        assertNotNull(userCompanyService.createUserCompanyByCode(createReqVO));

    }

    @Test
    void validateUserExists() {
    }

    @Test
    void validateUserUnExists() {
    }

    @Test
    void validateCompanyExists() {
    }

    @Test
    void validateCompanyExistsByCode() {
    }

    @Test
    void getCompanyByUser() {

        CompanyDO com = CompanyDO.builder()
                .id(1L)
                .name("小公司")
                .address(randomString())
                .phone(randomString())
                .code("swg")
                .build();

        companyMapper.insert(com);

        UserCompanyDO use = UserCompanyDO.builder()
                .id(1L)
                .companyId(1L)
                .userId(1L)
                .build();

        userCompanyMapper.insert(use);

        assertEquals(1L,userCompanyService.getCompanyByUser(1L).getId());


    }

    @Test
    void assignCustomerRole() {
    }

    @Test
    void assignNormalUserRole() {
        UserCompanyDO use = UserCompanyDO.builder()
                .id(1L)
                .companyId(1L)
                .userId(1L)
                .build();

        userCompanyMapper.insert(use);

        Mockito.when(roleService.getRoleByCode(RoleCodeEnum.NORMAL_USER.getCode())).thenReturn(
                new RoleDO().setId(1L).setCode("normal_user").setName("普通用户")
        );

        userCompanyService.assignNormalUserRole(1L);

    }

    @Test
    void deleteCustomerRole() {
    }
}