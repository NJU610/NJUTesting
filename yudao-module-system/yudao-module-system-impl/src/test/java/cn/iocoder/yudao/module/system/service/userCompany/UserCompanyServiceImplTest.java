//package cn.iocoder.yudao.module.system.service.userCompany;
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
