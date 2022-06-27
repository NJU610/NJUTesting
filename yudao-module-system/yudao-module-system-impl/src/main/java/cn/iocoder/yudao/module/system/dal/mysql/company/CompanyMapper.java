package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.CompanyDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.*;

/**
 * 公司 Mapper
 *
 * @author qjy
 */
@Mapper
public interface CompanyMapper extends BaseMapperX<CompanyDO> {


    default CompanyDO selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<CompanyDO>().eq(CompanyDO::getCode, code));
    }

    default boolean existsByNameExceptionMe(String name, Long id) {
        return selectCount(new LambdaQueryWrapperX<CompanyDO>().
                eq(CompanyDO::getName, name).
                ne(CompanyDO::getId, id)) > 0;
    }

    default boolean existsByName(String name) {
        return selectCount(new LambdaQueryWrapperX<CompanyDO>().eq(CompanyDO::getName, name)) > 0;
    }

    default boolean existsByCode(String code) {
        return selectCount(new LambdaQueryWrapperX<CompanyDO>().eq(CompanyDO::getCode, code)) > 0;
    }

    default PageResult<CompanyDO> selectPage(CompanyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, reqVO.getName())
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default PageResult<CompanyDO> selectPageByName(CompanyPageReqVO reqVO, String name) {
        return selectPage(reqVO,
                new LambdaQueryWrapperX<CompanyDO>()
                .like(CompanyDO::getName, name)
                .orderByDesc(CompanyDO::getId));
    }

    default PageResult<CompanyDO> selectPageByNameList(CompanyPageReqVO reqVO, ArrayList<String> nameList) {
        return selectPage(reqVO,
                new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getName, nameList)
                .orderByDesc(CompanyDO::getId));
    }

    default PageResult<CompanyDO> selectPageByCode(CompanyPageReqVO reqVO, String code) {
        return selectPage(reqVO,
                new LambdaQueryWrapperX<CompanyDO>()
                .eq(CompanyDO::getCode, code)
                .orderByDesc(CompanyDO::getId));
    }

    default PageResult<CompanyDO> selectPageByCodeList(CompanyPageReqVO reqVO, ArrayList<String> codeList) {
        return selectPage(reqVO,
                new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getCode, codeList)
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectList(CompanyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, reqVO.getName())
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByName(CompanyExportReqVO reqVO, String name) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, name)
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListById(CompanyExportReqVO reqVO, String id){
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .eqIfPresent(CompanyDO::getId, id)
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByAddress(CompanyExportReqVO reqVO, String address){
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .eqIfPresent(CompanyDO::getAddress, address)
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByName(String name) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .like(CompanyDO::getName, name));
    }

    default List<CompanyDO> selectListByCode(CompanyExportReqVO reqVO, String code) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, code)
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByCodeList(CompanyExportReqVO reqVO, ArrayList<String> codeList) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getCode, codeList)
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByNameList(CompanyExportReqVO reqVO, ArrayList<String> nameList) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getName, nameList)
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByIdList(CompanyExportReqVO reqVO, ArrayList<String> idList) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getId, idList)
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

    default List<CompanyDO> selectListByAddressList(CompanyExportReqVO reqVO, ArrayList<String> addressList) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .in(CompanyDO::getAddress, addressList)
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

}