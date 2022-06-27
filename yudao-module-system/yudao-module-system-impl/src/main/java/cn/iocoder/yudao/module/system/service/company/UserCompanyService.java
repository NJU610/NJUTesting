package cn.iocoder.yudao.module.system.service.company;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.COMPANY_NOT_EXISTS;

/**
 * 用户公司关联 Service 接口
 *
 * @author qjy
 */
public interface UserCompanyService {

    /**
     * 创建用户公司关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserCompany(@Valid UserCompanyCreateReqVO createReqVO);

    /**
     * 更新用户公司关联
     *
     * @param updateReqVO 更新信息
     */
    void updateUserCompany(@Valid UserCompanyUpdateReqVO updateReqVO);

    /**
     * 删除用户公司关联
     *
     * @param id 编号
     */
    void deleteUserCompany(Long id);

    /**
     * 获得用户公司关联
     *
     * @param id 编号
     * @return 用户公司关联
     */
    UserCompanyDO getUserCompany(Long id);

    /**
     * 获得用户公司关联列表
     *
     * @param ids 编号
     * @return 用户公司关联列表
     */
    List<UserCompanyDO> getUserCompanyList(Collection<Long> ids);

    /**
     * 获得用户公司关联分页
     *
     * @param pageReqVO 分页查询
     * @return 用户公司关联分页
     */
    PageResult<UserCompanyDO> getUserCompanyPage(UserCompanyPageReqVO pageReqVO);

    /**
     * 获得用户公司关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 用户公司关联列表
     */
    List<UserCompanyDO> getUserCompanyList(UserCompanyExportReqVO exportReqVO);

    /**
     * 获得用户对应的公司, 用于 Excel 导出
     *
     * @param userId 查询条件
     * @return 用户对应的公司
     */
    CompanyDO getCompanyByUser(Long userId);

    /**
     * 创建用户-公司关联, 用于 Excel 导出
     *
     * @param createReqVO 查询条件
     * @return 用户公司关联号
     */
    Long createUserCompanyByCode(UserCompanyCreateByCodeReqVO createReqVO);

    /**
     * 根据用户id检查用户是否已有用户-公司关联
     *
     * @param userId 查询条件
     * @return void
     */
    void validateUserExists(Long userId);

    /**
     * 根据用户id检查用户是否没有用户-公司关联
     *
     * @param userId 查询条件
     * @return void
     */
    void validateUserUnExists(Long userId);

    /**
     * 根据用户-公司关联id检查用户是否已有用户-公司关联
     *
     * @param Id 查询条件
     * @return void
     */
    void validateUserUnExistsById(Long Id);

    /**
     * 根据公司id检查公司是否存在
     *
     * @param companyId 查询条件
     * @return void
     */
    void validateCompanyExists(Long companyId);

    /**
     * 根据公司认证码检查公司是否存在
     *
     * @param code 查询条件
     * @return void
     */
    void validateCompanyExistsByCode(String code);

    /**
     * 根据公司名称检查公司是否存在
     *
     * @param name 查询条件
     * @return void
     */
    void validateCompanyExistsByName(String name);

    /**
     * 根据用户id设置特定用户角色为客户（可以发起委托）
     *
     * @param id 查询条件
     * @return void
     */
    void assignCustomerRole(Long id);

    /**
     * 根据用户id设置特定用户角色为普通用户（不可以发起委托）
     *
     * @param id 查询条件
     * @return void
     */
    void assignNormalUserRole(Long id);

    /**
     * 根据用户id删除特定用户角色的客户权限（不可以发起委托）
     *
     * @param id 查询条件
     * @return void
     */
    void deleteCustomerRole(Long id);

    /**
     * 获得公司对应的用户-公司关联, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return void
     */
    List<UserCompanyDO> getUserCompanyListByCompany(UserCompanyExportReqVO exportReqVO);

    /**
     * 获得特定时间段的用户-公司关联, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return void
     */
    List<UserCompanyDO> getUserCompanyListByTime(UserCompanyExportReqVO exportReqVO);
}
