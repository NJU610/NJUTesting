package cn.iocoder.yudao.module.system.service.permission;


import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.permission.FrontMenuConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import cn.iocoder.yudao.module.system.dal.mysql.permission.FrontMenuMapper;
import cn.iocoder.yudao.module.system.dal.mysql.permission.RoleFrontMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 前台菜单 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class FrontMenuServiceImpl implements FrontMenuService {

    @Resource
    private FrontMenuMapper frontMenuMapper;

    @Resource
    private RoleFrontMenuMapper roleFrontMenuMapper;

    @Override
    public Long createFrontMenu(FrontMenuCreateReqVO createReqVO) {
        // 插入
        FrontMenuDO frontMenu = FrontMenuConvert.INSTANCE.convert(createReqVO);
        frontMenuMapper.insert(frontMenu);
        // 返回
        return frontMenu.getId();
    }

    @Override
    public void updateFrontMenu(FrontMenuUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateFrontMenuExists(updateReqVO.getId());
        // 更新
        FrontMenuDO updateObj = FrontMenuConvert.INSTANCE.convert(updateReqVO);
        frontMenuMapper.updateById(updateObj);
    }

    @Override
    public void deleteFrontMenu(Long id) {
        // 校验存在
        this.validateFrontMenuExists(id);
        // 删除
        frontMenuMapper.deleteById(id);
        // 删除角色菜单关联
        roleFrontMenuMapper.deleteListByMenuId(id);
    }

    private void validateFrontMenuExists(Long id) {
        if (frontMenuMapper.selectById(id) == null) {
            throw exception(FRONT_MENU_NOT_EXISTS);
        }
    }

    @Override
    public FrontMenuDO getFrontMenu(Long id) {
        return frontMenuMapper.selectById(id);
    }

    @Override
    public List<FrontMenuDO> getFrontMenuList(Collection<Long> ids) {
        return frontMenuMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FrontMenuDO> getFrontMenuPage(FrontMenuPageReqVO pageReqVO) {
        return frontMenuMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FrontMenuDO> getFrontMenuList(FrontMenuExportReqVO exportReqVO) {
        return frontMenuMapper.selectList(exportReqVO);
    }

    @Override
    public void validFrontMenus(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得前台菜单信息
        List<FrontMenuDO> menuList = frontMenuMapper.selectBatchIds(ids);
        Map<Long, FrontMenuDO> frontMenuMap = CollectionUtils.convertMap(menuList, FrontMenuDO::getId);
        // 校验
        ids.forEach(id -> {
            FrontMenuDO frontMenu = frontMenuMap.get(id);
            if (frontMenu == null) {
                throw exception(FRONT_MENU_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(frontMenu.getStatus())) {
                throw exception(FRONT_MENU_NOT_VALID, frontMenu.getName());
            }
        });
    }

}
