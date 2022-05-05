package cn.iocoder.yudao.module.system.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.company.vo.UserCompanyPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户公司关联 Mapper
 *
 * @author qjy
 */
@Mapper
public interface UserCompanyMapper extends BaseMapperX<UserCompanyDO> {

    default UserCompanyDO selectByUser(Long userId) {
        return selectOne(new LambdaQueryWrapperX<UserCompanyDO>()
                .eq(UserCompanyDO::getUserId, userId));
    }

    default boolean existsByUser(Long userId) {
        return selectCount(new LambdaQueryWrapperX<UserCompanyDO>()
                .eq(UserCompanyDO::getUserId, userId)) > 0;
    }

    default int UpdateByUser(UserCompanyDO userCompanyDO) {
        return update(userCompanyDO, new LambdaQueryWrapperX<UserCompanyDO>()
                .eq(UserCompanyDO::getUserId, userCompanyDO.getUserId()));
    }

    default PageResult<UserCompanyDO> selectPage(UserCompanyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserCompanyDO>()
                .eqIfPresent(UserCompanyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserCompanyDO::getCompanyId, reqVO.getCompanyId())
                .betweenIfPresent(UserCompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(UserCompanyDO::getId));
    }

    default List<UserCompanyDO> selectList(UserCompanyExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<UserCompanyDO>()
                .eqIfPresent(UserCompanyDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserCompanyDO::getCompanyId, reqVO.getCompanyId())
                .betweenIfPresent(UserCompanyDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(UserCompanyDO::getId));
    }

}
