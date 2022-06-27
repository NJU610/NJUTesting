package cn.iocoder.yudao.module.system.service.company;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;
import cn.iocoder.yudao.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.userCompany.UserCompanyConvert;
import cn.iocoder.yudao.module.system.dal.mysql.company.UserCompanyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户公司关联 Service 测试类
 *
 * @author jzx
 */
@Service
@Validated
public class UserCompanyServiceImpl implements UserCompanyService {

    @Resource
    private UserCompanyMapper userCompanyMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    public Long createUserCompany(UserCompanyCreateReqVO createReqVO) {
        this.validateUserUnExists(createReqVO.getUserId());
        this.validateCompanyExists(createReqVO.getCompanyId());
        // 插入
        UserCompanyDO userCompany = UserCompanyConvert.INSTANCE.convert(createReqVO);
        userCompanyMapper.insert(userCompany);
        // 返回
        assignCustomerRole(userCompany.getUserId());
        return userCompany.getId();
    }

    @Override
    public void updateUserCompany(UserCompanyUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateUserExists(updateReqVO.getUserId());
        this.validateCompanyExists(updateReqVO.getCompanyId());
        // 更新
        UserCompanyDO userCompany = UserCompanyConvert.INSTANCE.convert(updateReqVO);
        userCompanyMapper.UpdateByUser(userCompany);
    }

    @Override
    public void deleteUserCompany(Long id) {
        // 校验存在
        this.validateUserCompanyExists(id);
        UserCompanyDO userCompany = userCompanyMapper.selectById(id);
        // 删除
        userCompanyMapper.deleteById(id);
        // 恢复为普通用户
        deleteCustomerRole(userCompany.getUserId());
    }

    private void validateUserCompanyExists(Long id) {
        if (userCompanyMapper.selectById(id) == null) {
            throw exception(USER_COMPANY_NOT_EXISTS);
        }
    }

    @Override
    public UserCompanyDO getUserCompany(Long id) {
        return userCompanyMapper.selectById(id);
    }

    @Override
    public List<UserCompanyDO> getUserCompanyList(Collection<Long> ids) {
        return userCompanyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UserCompanyDO> getUserCompanyPage(UserCompanyPageReqVO pageReqVO) {
        return userCompanyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UserCompanyDO> getUserCompanyList(UserCompanyExportReqVO exportReqVO) {
        return userCompanyMapper.selectList(exportReqVO);
    }

    @Override
    public Long createUserCompanyByCode(UserCompanyCreateByCodeReqVO createReqVO) {
        if (createReqVO.getUserId() == null) {
            createReqVO.setUserId(getLoginUserId());
        }
        this.validateUserUnExists(createReqVO.getUserId());

        CompanyDO company = companyMapper.selectByCode(createReqVO.getCode());
        if (company == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
        UserCompanyDO userCompany = UserCompanyDO.builder()
                .userId(createReqVO.getUserId() == null ? getLoginUserId() : createReqVO.getUserId())
                .companyId(company.getId()).build();
        userCompanyMapper.insert(userCompany);

        assignCustomerRole(userCompany.getUserId());
        return userCompany.getId();
    }

    public void validateUserExists(Long userId) {
        if (!userCompanyMapper.existsByUser(userId)) {
            throw exception(USER_COMPANY_NOT_EXISTS);
        }
    }

    public void validateUserUnExists(Long userId) {
        if (userCompanyMapper.existsByUser(userId)) {
            throw exception(USER_COMPANY_EXISTS);
        }
    }

    public void validateUserUnExistsById(Long Id){
        if (userCompanyMapper.existsById(Id)){
            throw exception(USER_COMPANY_EXISTS);
        }
    }

    public void validateCompanyExists(Long companyId) {
        CompanyDO company = companyMapper.selectById(companyId);
        if (company == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    public void validateCompanyExistsByCode(String code) {
        CompanyDO company = companyMapper.selectByCode(code);
        if (company == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    public void validateCompanyExistsByName(String name) {

        if ( companyMapper.existsByName(name) == false) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    public CompanyDO getCompanyByUser(Long userId) {
        UserCompanyDO userCompany = userCompanyMapper.selectByUser(userId);
        if (userCompany == null) {
            throw exception(USER_COMPANY_NOT_EXISTS);
        }
        return companyMapper.selectById(userCompany.getCompanyId());
    }

    public UserCompanyDO getUserCompanyByUser(Long userId){
        return userCompanyMapper.selectByUser(userId);
    }

    public List<UserCompanyDO> getUserCompanyListByCompany(UserCompanyExportReqVO exportReqVO){
        return userCompanyMapper.selectListByCompany(exportReqVO);
    }

    public List<UserCompanyDO> getUserCompanyListByTime(UserCompanyExportReqVO exportReqVO){
        return userCompanyMapper.selectListByTime(exportReqVO);
    }

    public void assignCustomerRole(Long id){
        RoleDO customer = roleService.getRoleByCode(RoleCodeEnum.CUSTOMER.getCode());
        Set<Long> roleIds = new HashSet<>(permissionService.getUserRoleIdListByUserId(id));
        roleIds.add(customer.getId());
        permissionService.assignUserRole(id, roleIds);
    }

    public void assignNormalUserRole(Long id){
        RoleDO normal_user = roleService.getRoleByCode(RoleCodeEnum.NORMAL_USER.getCode());
        Set<Long> roleIds = new HashSet<>(permissionService.getUserRoleIdListByUserId(id));
        roleIds.add(normal_user.getId());
        permissionService.assignUserRole(id, roleIds);
    }

    public void deleteCustomerRole(Long id){
        RoleDO customer = roleService.getRoleByCode(RoleCodeEnum.CUSTOMER.getCode());
        Set<Long> roleIds = new HashSet<>(permissionService.getUserRoleIdListByUserId(id));
        roleIds.remove(customer.getId());
        permissionService.assignUserRole(id, roleIds);
    }

}
