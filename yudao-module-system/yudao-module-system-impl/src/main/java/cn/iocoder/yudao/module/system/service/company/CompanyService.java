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
     *根据名称获得公司分页
     *
     * @param pageReqVO 分页查询
     * @param name 公司名称
     * @return
     */
    PageResult<CompanyDO> getCompanyPageByName(CompanyPageReqVO pageReqVO, String name);

    /**
     *根据名称列表获得公司分页
     *
     *
     * @param pageReqVO 分页查询
     * @param nameList 公司名称列表
     * @return
     */
    PageResult<CompanyDO> getCompanyPageByNameList(CompanyPageReqVO pageReqVO, ArrayList<String> nameList);

    /**
     *根据名称获得公司分页
     *
     * @param pageReqVO 分页查询
     * @param code 公司代码
     * @return
     */
    PageResult<CompanyDO> getCompanyPageByCode(CompanyPageReqVO pageReqVO, String code);

    /**
     *根据名称列表获得公司分页
     *
     *
     * @param pageReqVO 分页查询
     * @param codeList 公司代码列表
     * @return
     */
    PageResult<CompanyDO> getCompanyPageByCodeList(CompanyPageReqVO pageReqVO, ArrayList<String> codeList);

    /**
     * 获得公司列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公司列表
     */
    List<CompanyDO> getCompanyList(CompanyExportReqVO exportReqVO);


    /**
     * 获得公司列表
     *
     * @return 公司列表
     */
    List<CompanyDO> getCompanyListAll();

    /**
     * 根据公司名称获得公司
     *
     * @param name 公司名称
     */
    List<CompanyDO> getCompanyListByName(String name);

    /**
     * 根据公司名称获得公司
     *
     * @param name 公司名称
     */
    List<CompanyDO> getCompanyListByName(CompanyExportReqVO reqVO, String name);

    /**
    * 根据公司名称列表获得公司列表
    *
    * @param names 公司名称列表
    */
    List<CompanyDO> getCompanyListByNameList(CompanyExportReqVO reqVO, ArrayList<String> names);

    /**
     * 根据id获得公司
     *
     *
     *
     * @param id 表id
     */
    List<CompanyDO> getCompanyListById(CompanyExportReqVO reqVO, String id);

    /**
     * 根据id列表获得公司列表
     *
     *
     * @param ids id列表
     */
    List<CompanyDO> getCompanyListByIdList(CompanyExportReqVO reqVO, ArrayList<String> ids);

    /**
     * 根据公司地址获得公司
     *
     *
     * @param address 公司地址
     */
    List<CompanyDO> getCompanyListByAddress(CompanyExportReqVO reqVO, String address);


    /**
     * 根据公司地址列表获得公司列表
     *
     *
     * @param addresses 公司地址列表
     */
    List<CompanyDO> getCompanyListByAddressList(CompanyExportReqVO reqVO, ArrayList<String> addresses);

    /**
     * 根据公司代码获得公司列表
     *
     * @param code 公司名称列表
     */
    List<CompanyDO> getCompanyListByCode(CompanyExportReqVO reqVO, String code);

    /**
     * 根据公司代码列表获得公司列表
     *
     * @param codes 公司名称列表
     */
    List<CompanyDO> getCompanyListByCodeList(CompanyExportReqVO reqVO, ArrayList<String> codes);
}
