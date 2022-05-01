package cn.iocoder.yudao.module.system.service.company;

import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.userCompany.UserCompanyConvert;
import cn.iocoder.yudao.module.system.dal.mysql.company.UserCompanyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户公司关联 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class UserCompanyServiceImpl implements UserCompanyService {

    @Resource
    private UserCompanyMapper userCompanyMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Long createUserCompany(UserCompanyCreateReqVO createReqVO) {
        this.validateUserUnExists(createReqVO.getUserId());
        // 插入
        UserCompanyDO userCompany = UserCompanyConvert.INSTANCE.convert(createReqVO);
        userCompanyMapper.insert(userCompany);
        // 返回
        return userCompany.getId();
    }

    @Override
    public void updateUserCompany(UserCompanyUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateUserExists(updateReqVO.getUserId());
        // 更新
        UserCompanyDO userCompany = UserCompanyConvert.INSTANCE.convert(updateReqVO);
        userCompanyMapper.UpdateByUser(userCompany);
    }

    @Override
    public void deleteUserCompany(Long id) {
        // 校验存在
        this.validateUserCompanyExists(id);
        // 删除
        userCompanyMapper.deleteById(id);
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

    public Long createUserCompanyByCode(UserCompanyCreateByCodeReqVO createReqVO) {
        this.validateUserUnExists(createReqVO.getUserId());
        
        CompanyDO company = companyMapper.selectByCode(createReqVO.getCode());
        if (company == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
        UserCompanyDO userCompany = UserCompanyDO.builder().userId(createReqVO.getUserId()).companyId(company.getId()).build();
        userCompanyMapper.insert(userCompany);

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
}
