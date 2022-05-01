package cn.iocoder.yudao.module.system.service.company;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

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

    Long createUserCompanyByCode(UserCompanyCreateByCodeReqVO createReqVO);
}
