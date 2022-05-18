package cn.iocoder.yudao.module.system.service.permission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 前台菜单 Service 接口
 *
 * @author qjy
 */
public interface FrontMenuService {

    /**
     * 创建前台菜单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFrontMenu(@Valid FrontMenuCreateReqVO createReqVO);

    /**
     * 更新前台菜单
     *
     * @param updateReqVO 更新信息
     */
    void updateFrontMenu(@Valid FrontMenuUpdateReqVO updateReqVO);

    /**
     * 删除前台菜单
     *
     *
     * @param id 编号
     */
    void deleteFrontMenu(Long id);

    /**
     * 获得前台菜单
     *
     * @param id 编号
     * @return 前台菜单
     */
    FrontMenuDO getFrontMenu(Long id);

    /**
     * 获得前台菜单列表
     *
     * @param ids 编号
     * @return 前台菜单列表
     */
    List<FrontMenuDO> getFrontMenuList(Collection<Long> ids);

    /**
     * 获得前台菜单分页
     *
     * @param pageReqVO 分页查询
     * @return 前台菜单分页
     */
    PageResult<FrontMenuDO> getFrontMenuPage(FrontMenuPageReqVO pageReqVO);

    /**
     * 获得前台菜单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 前台菜单列表
     */
    List<FrontMenuDO> getFrontMenuList(FrontMenuExportReqVO exportReqVO);

    void validFrontMenus(Collection<Long> ids);
}
