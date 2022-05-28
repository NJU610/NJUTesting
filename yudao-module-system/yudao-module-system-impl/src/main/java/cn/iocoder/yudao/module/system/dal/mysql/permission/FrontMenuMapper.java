package cn.iocoder.yudao.module.system.dal.mysql.permission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 前台菜单 Mapper
 *
 * @author qjy
 */
@Mapper
public interface FrontMenuMapper extends BaseMapperX<FrontMenuDO> {

    default PageResult<FrontMenuDO> selectPage(FrontMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FrontMenuDO>()
                .eqIfPresent(FrontMenuDO::getId, reqVO.getId())
                .likeIfPresent(FrontMenuDO::getName, reqVO.getName())
                .eqIfPresent(FrontMenuDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FrontMenuDO::getHideInMenu, reqVO.getHideInMenu())
                .betweenIfPresent(FrontMenuDO::getCreateTime, reqVO.getBeginTime(), reqVO.getEndTime())
                .orderByDesc(FrontMenuDO::getId));
    }

    default List<FrontMenuDO> selectList(FrontMenuExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FrontMenuDO>()
                .eqIfPresent(FrontMenuDO::getId, reqVO.getId())
                .orderByDesc(FrontMenuDO::getId));
    }

    default List<FrontMenuDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<FrontMenuDO>()
                .eq(FrontMenuDO::getStatus, status)
                .orderByDesc(FrontMenuDO::getId));
    }
}
