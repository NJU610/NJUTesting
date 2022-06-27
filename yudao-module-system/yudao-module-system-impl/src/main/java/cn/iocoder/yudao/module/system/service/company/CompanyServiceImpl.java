package cn.iocoder.yudao.module.system.service.company;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.company.CompanyConvert;
import cn.iocoder.yudao.module.system.dal.mysql.company.CompanyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 公司 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Long createCompany(CompanyCreateReqVO createReqVO) {
        // 插入
        validateNameExists(createReqVO.getName());
        CompanyDO company = CompanyConvert.INSTANCE.convert(createReqVO);
        while (company.getCode() == null) {
            //8位只包含字母和数字的字符串
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            if (!companyMapper.existsByCode(code)) {
                company.setCode(code);
            }
        }
        companyMapper.insert(company);
        // 返回
        return company.getId();
    }

    @Override
    public void updateCompany(CompanyUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateCompanyExists(updateReqVO.getId());
        this.validateNameExistsExceptionMe(updateReqVO.getName(), updateReqVO.getId());
        // 更新
        CompanyDO updateObj = CompanyConvert.INSTANCE.convert(updateReqVO);
        companyMapper.updateById(updateObj);
    }

    @Override
    public void deleteCompany(Long id) {
        // 校验存在
        this.validateCompanyExists(id);
        // 删除
        companyMapper.deleteById(id);
    }

    private void validateNameExists(String name) {
        if (companyMapper.existsByName(name)) {
            throw exception(COMPANY_NAME_EXISTS);
        }
    }

    private void validateNameExistsExceptionMe(String name, Long id) {
        if (companyMapper.existsByNameExceptionMe(name, id)) {
            throw exception(COMPANY_NAME_EXISTS);
        }
    }

    private void validateCompanyExists(Long id) {
        if (companyMapper.selectById(id) == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    @Override
    public CompanyDO getCompany(Long id) {
        return companyMapper.selectById(id);
    }

    @Override
    public List<CompanyDO> getCompanyList(Collection<Long> ids) {
        return companyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO) {
        return companyMapper
                .selectPage(pageReqVO);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPageByName(CompanyPageReqVO pageReqVO, String name) {
        return companyMapper
                .selectPageByName(pageReqVO, name);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPageByNameList(CompanyPageReqVO pageReqVO, ArrayList<String> nameList) {
        return companyMapper
                .selectPageByNameList(pageReqVO, nameList);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPageByCode(CompanyPageReqVO pageReqVO, String code) {
        return companyMapper
                .selectPageByCode(pageReqVO, code);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPageByCodeList(CompanyPageReqVO pageReqVO, ArrayList<String> codeList) {
        return companyMapper
                .selectPageByCodeList(pageReqVO, codeList);
    }

    @Override
    public List<CompanyDO> getCompanyList(CompanyExportReqVO exportReqVO) {
        return companyMapper.selectList(exportReqVO);
    }

    @Override
    public List<CompanyDO> getCompanyListAll() {
        return companyMapper.selectList();
    }

    public List<CompanyDO> getCompanyListByRandom(CompanyExportReqVO exportReqVO) {
        List<CompanyDO> list = companyMapper.selectList(exportReqVO);
        Collections.shuffle(list);
        return list;
    }

    public List<CompanyDO> getCompanyListByRandom(CompanyExportReqVO exportReqVO, int num) {
        List<CompanyDO> list = companyMapper.selectList(exportReqVO);
        Collections.shuffle(list);
        return list.subList(0, num);
    }

    public List<CompanyDO> getCompanyListByName(CompanyExportReqVO exportReqVO) {
        List<CompanyDO> list = companyMapper.selectList(exportReqVO);
        Collections.sort(list, new Comparator<CompanyDO>() {
            @Override
            public int compare(CompanyDO o1, CompanyDO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }



    @Override
    public List<CompanyDO> getCompanyListByName(String name) {
        return companyMapper
                .selectListByName(name);
    }

    @Override
    public List<CompanyDO> getCompanyListByNameList(CompanyExportReqVO reqVO, ArrayList<String> names){
        return companyMapper
                .selectListByNameList(reqVO,names);
    }

    @Override
    public List<CompanyDO> getCompanyListByName(CompanyExportReqVO reqVO, String name){
        return companyMapper
                .selectListByName(reqVO,name);
    }

    @Override
    public List<CompanyDO> getCompanyListById(CompanyExportReqVO reqVO, String id){
        return companyMapper
                .selectListById(reqVO,id);
    }

    @Override
    public List<CompanyDO> getCompanyListByIdList(CompanyExportReqVO reqVO, ArrayList<String> ids){
        return companyMapper
                .selectListByIdList(reqVO,ids);
    }

    @Override
    public List<CompanyDO> getCompanyListByAddress(CompanyExportReqVO reqVO, String address){
        return companyMapper
                .selectListByAddress(reqVO,address);
    }

    @Override
    public List<CompanyDO> getCompanyListByAddressList(CompanyExportReqVO reqVO, ArrayList<String> addresses){
        return companyMapper
                .selectListByAddressList(reqVO,addresses);
    }

    @Override
    public List<CompanyDO> getCompanyListByCode(CompanyExportReqVO reqVO, String code){
        return companyMapper
                .selectListByCode(reqVO,code);
    }

    @Override
    public List<CompanyDO> getCompanyListByCodeList(CompanyExportReqVO reqVO, ArrayList<String> codes){
        return companyMapper
                .selectListByCodeList(reqVO,codes);
    }
}
