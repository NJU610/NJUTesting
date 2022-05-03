package cn.iocoder.yudao.module.system.service.delegation;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 委托 Service 接口
 *
 * @author lyw
 */
public interface DelegationService {

    /**
     * 创建委托
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDelegation(@Valid DelegationCreateReqVO createReqVO);

    /**
     * 更新委托
     *
     * @param updateReqVO 更新信息
     */
    void updateDelegation(@Valid DelegationUpdateReqVO updateReqVO);

    /**
     * 提交委托
     *
     * @param  id 编号
     */
    void submitDelegation(Long id);

    /**
     * 保存软件项目委托测试申请表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable2(@Valid DelegationSaveTableReqVo updateReqVO);

    /**
     * 保存委托测试软件功能列表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable3(@Valid DelegationSaveTableReqVo updateReqVO);

    /**
     * 删除委托
     *
     * @param id 编号
     */
    void deleteDelegation(Long id);

    /**
     * 获得委托
     *
     * @param id 编号
     * @return 委托
     */
    DelegationRespVO getDelegation(Long id);

    /**
     * 获得当前用户所有委托
     *
     * @return 委托列表
     */
    List<DelegationRespVO> getDelegationsByCurrentUser();

    /**
     * 获取特定用户所有委托
     *
     * @param userId 用户编号
     * @return 委托列表
     */
    List<DelegationRespVO> getDelegationsByCreator(Long userId);

    /**
     * 获取未被接收的委托
     *
     * @return 委托列表
     */
    List<DelegationRespVO> getDelegationsNotAccepted();

    /**
     * 获取软件项目委托测试申请表
     *
     * @param id 表格编号
     * @return 委托列表
     */
    String getDelegationTable2(String id);

    /**
     * 委托测试软件功能列表
     *
     * @param id 表格编号
     * @return 委托列表
     */
    String getDelegationTable3(String id);

    /**
     * 获得委托列表
     *
     * @param ids 编号
     * @return 委托列表
     */
    List<DelegationRespVO> getDelegationList(Collection<Long> ids);

    /**
     * 获得委托分页
     *
     * @param pageReqVO 分页查询
     * @return 委托分页
     */
    PageResult<DelegationDO> getDelegationPage(DelegationPageReqVO pageReqVO);

    /**
     * 获得委托列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 委托列表
     */
    List<DelegationDO> getDelegationList(DelegationExportReqVO exportReqVO);

}
