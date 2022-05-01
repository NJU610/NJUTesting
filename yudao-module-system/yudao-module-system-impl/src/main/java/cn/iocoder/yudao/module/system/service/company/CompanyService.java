package cn.iocoder.yudao.module.system.service.company;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 公司 Service 接口
 *
 * @author qjy
 */
public interface CompanyService {

    /**
     * 创建公司
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompany(@Valid CompanyCreateReqVO createReqVO);

    /**
     * 更新公司
     *
     * @param updateReqVO 更新信息
     */
    void updateCompany(@Valid CompanyUpdateReqVO updateReqVO);

    /**
     * 删除公司
     *
     * @param id 编号
     */
    void deleteCompany(Long id);

    /**
     * 获得公司
     *
     * @param id 编号
     * @return 公司
     */
    CompanyDO getCompany(Long id);

    /**
     * 获得公司列表
     *
     * @param ids 编号
     * @return 公司列表
     */
    List<CompanyDO> getCompanyList(Collection<Long> ids);

    /**
     * 获得公司分页
     *
     * @param pageReqVO 分页查询
     * @return 公司分页
     */
    PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO);

    /**
     * 获得公司列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公司列表
     */
    List<CompanyDO> getCompanyList(CompanyExportReqVO exportReqVO);

}
