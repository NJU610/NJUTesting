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

    default List<CompanyDO> selectList(CompanyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, reqVO.getName())
                .eqIfPresent(CompanyDO::getAddress, reqVO.getAddress())
                .eqIfPresent(CompanyDO::getPhone, reqVO.getPhone())
                .eqIfPresent(CompanyDO::getCode, reqVO.getCode())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

}
