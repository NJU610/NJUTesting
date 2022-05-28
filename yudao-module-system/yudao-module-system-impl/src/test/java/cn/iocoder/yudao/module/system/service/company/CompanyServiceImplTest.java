//package cn.iocoder.yudao.module.system.service.company;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import javax.annotation.Resource;
//
//import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
//
//import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
//import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
//import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;
//import cn.iocoder.yudao.framework.common.pojo.PageResult;
//
//import javax.annotation.Resource;
//import org.springframework.context.annotation.Import;
//import java.util.*;
//
//import static cn.hutool.core.util.RandomUtil.*;
//import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
//import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
//import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
//import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
//import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
//* {@link CompanyServiceImpl} 的单元测试类
//*
//* @author qjy
//*/
//@Import(CompanyServiceImpl.class)
//public class CompanyServiceImplTest extends BaseDbUnitTest {
//
//    @Resource
//    private CompanyServiceImpl companyService;
//
//    @Resource
//    private CompanyMapper companyMapper;
//
//    @Test
//    public void testCreateCompany_success() {
//        // 准备参数
//        CompanyCreateReqVO reqVO = randomPojo(CompanyCreateReqVO.class);
//
//        // 调用
//        Long companyId = companyService.createCompany(reqVO);
//        // 断言
//        assertNotNull(companyId);
//        // 校验记录的属性是否正确
//        CompanyDO company = companyMapper.selectById(companyId);
//        assertPojoEquals(reqVO, company);
//    }
//
//    @Test
//    public void testUpdateCompany_success() {
//        // mock 数据
//        CompanyDO dbCompany = randomPojo(CompanyDO.class);
//        companyMapper.insert(dbCompany);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        CompanyUpdateReqVO reqVO = randomPojo(CompanyUpdateReqVO.class, o -> {
//            o.setId(dbCompany.getId()); // 设置更新的 ID
//        });
//
//        // 调用
//        companyService.updateCompany(reqVO);
//        // 校验是否更新正确
//        CompanyDO company = companyMapper.selectById(reqVO.getId()); // 获取最新的
//        assertPojoEquals(reqVO, company);
//    }
//
//    @Test
//    public void testUpdateCompany_notExists() {
//        // 准备参数
//        CompanyUpdateReqVO reqVO = randomPojo(CompanyUpdateReqVO.class);
//
//        // 调用, 并断言异常
//        assertServiceException(() -> companyService.updateCompany(reqVO), COMPANY_NOT_EXISTS);
//    }
//
//    @Test
//    public void testDeleteCompany_success() {
//        // mock 数据
//        CompanyDO dbCompany = randomPojo(CompanyDO.class);
//        companyMapper.insert(dbCompany);// @Sql: 先插入出一条存在的数据
//        // 准备参数
//        Long id = dbCompany.getId();
//
//        // 调用
//        companyService.deleteCompany(id);
//       // 校验数据不存在了
//       assertNull(companyMapper.selectById(id));
//    }
//
//    @Test
//    public void testDeleteCompany_notExists() {
//        // 准备参数
//        Long id = randomLongId();
//
//        // 调用, 并断言异常
//        assertServiceException(() -> companyService.deleteCompany(id), COMPANY_NOT_EXISTS);
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetCompanyPage() {
//       // mock 数据
//       CompanyDO dbCompany = randomPojo(CompanyDO.class, o -> { // 等会查询到
//           o.setName(null);
//           o.setAddress(null);
//           o.setPhone(null);
//           o.setCode(null);
//           o.setCreateTime(null);
//       });
//       companyMapper.insert(dbCompany);
//       // 测试 name 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setName(null)));
//       // 测试 address 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setAddress(null)));
//       // 测试 phone 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setPhone(null)));
//       // 测试 code 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setCode(null)));
//       // 测试 createTime 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setCreateTime(null)));
//       // 准备参数
//       CompanyPageReqVO reqVO = new CompanyPageReqVO();
//       reqVO.setName(null);
//       reqVO.setAddress(null);
//       reqVO.setPhone(null);
//       reqVO.setCode(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       PageResult<CompanyDO> pageResult = companyService.getCompanyPage(reqVO);
//       // 断言
//       assertEquals(1, pageResult.getTotal());
//       assertEquals(1, pageResult.getList().size());
//       assertPojoEquals(dbCompany, pageResult.getList().get(0));
//    }
//
//    @Test
//    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
//    public void testGetCompanyList() {
//       // mock 数据
//       CompanyDO dbCompany = randomPojo(CompanyDO.class, o -> { // 等会查询到
//           o.setName(null);
//           o.setAddress(null);
//           o.setPhone(null);
//           o.setCode(null);
//           o.setCreateTime(null);
//       });
//       companyMapper.insert(dbCompany);
//       // 测试 name 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setName(null)));
//       // 测试 address 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setAddress(null)));
//       // 测试 phone 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setPhone(null)));
//       // 测试 code 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setCode(null)));
//       // 测试 createTime 不匹配
//       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setCreateTime(null)));
//       // 准备参数
//       CompanyExportReqVO reqVO = new CompanyExportReqVO();
//       reqVO.setName(null);
//       reqVO.setAddress(null);
//       reqVO.setPhone(null);
//       reqVO.setCode(null);
//       reqVO.setBeginCreateTime(null);
//       reqVO.setEndCreateTime(null);
//
//       // 调用
//       List<CompanyDO> list = companyService.getCompanyList(reqVO);
//       // 断言
//       assertEquals(1, list.size());
//       assertPojoEquals(dbCompany, list.get(0));
//    }
//
//}


