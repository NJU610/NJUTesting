package cn.iocoder.yudao.module.system.service.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import com.alibaba.fastjson.JSONObject;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
     * @param submitReqVO 提交信息
     */
    void submitDelegation(@Valid DelegationSubmitReqVO submitReqVO);

    /**
     * 保存软件项目委托测试申请表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable2(@Valid DelegationSaveTableReqVO updateReqVO);

    /**
     * 保存委托测试软件功能列表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable3(@Valid DelegationSaveTableReqVO updateReqVO);

    /**
     * 保存软件文档评审表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable14(@Valid DelegationSaveTableReqVO updateReqVO);

    /**
     * 保存软件项目委托测试工作检查表
     *
     * @param updateReqVO 更新信息
     */
    void saveDelegationTable12(@Valid DelegationSaveTableReqVO updateReqVO);

    /**
     * 分配委托给市场部人员
     *
     * @param distributeReqVO 分配信息
     */
    void distributeDelegation2Mkt(@Valid DelegationDistributeReqVO distributeReqVO);

    /**
     * 分配委托给测试部人员
     *
     * @param distributeReqVO 分配信息
     */
    void distributeDelegation2Test(@Valid DelegationDistributeReqVO distributeReqVO);

    /**
     * 市场部人员审核委托通过
     *
     * @param auditReqVO 审核信息
     */
    void auditDelegationSuccessMkt(@Valid DelegationAuditReqVO auditReqVO);

    /**
     * 测试部人员审核委托通过
     *
     * @param auditReqVO 审核信息
     */
    void auditDelegationSuccessTest(@Valid DelegationAuditReqVO auditReqVO);

    /**
     * 市场部人员审核委托不通过
     *
     * @param auditReqVO 审核信息
     */
    void auditDelegationFailMkt(@Valid DelegationAuditReqVO auditReqVO);

    /**
     * 测试部人员审核委托不通过
     *
     * @param auditReqVO 审核信息
     */
    void auditDelegationFailTest(@Valid DelegationAuditReqVO auditReqVO);

    /**
     * 保存报价单
     *
     * @param offerSaveReqVO 保存信息
     */
    void saveOffer(@Valid DelegationSaveTableReqVO offerSaveReqVO);

    /**
     * 提交报价单
     *
     * @param offerSubmitReqVO 提交信息
     */
    void submitOffer(@Valid OfferSubmitReqVO offerSubmitReqVO);

    /**
     * 客户不接受报价
     *
     * @param offerRejectReqVO 拒绝信息
     */
    void rejectOffer(@Valid OfferRejectReqVO offerRejectReqVO);

    /**
     * 客户接受报价
     *
     * @param offerAcceptReqVO 接受信息
     */
    void acceptOffer(@Valid OfferAcceptReqVO offerAcceptReqVO);

    /**
     * 测试部主管填写项目编号
     *
     * @param reqVO 填写信息
     */
    void fillProjectId(@Valid DelegationFillProjReqVO reqVO);

    /**
     * 删除委托
     *
     * @param id 编号
     */
    void deleteDelegation(Long id);

    /**
     * 给respvo添加字段
     *
     *  @param respVO 响应信息
     */
    void addFields(DelegationRespVO respVO);

    /**
     * 给respvo列表添加字段
     *
     *  @param respVOs 响应信息
     */
    void addFields(List<DelegationRespVO> respVOs);

    /**
     * 获得委托
     *
     * @param id 编号
     * @return 委托
     */
    DelegationDO getDelegation(Long id);

    /**
     * 获得当前用户所有委托
     *
     * @return 委托列表
     */
    List<DelegationDO> getDelegationsByCurrentUser();

    /**
     * 获取特定用户所有委托
     *
     * @param userId 用户编号
     * @return 委托列表
     */
    List<DelegationDO> getDelegationsByCreator(Long userId);

    /**
     * 获取未被接收的委托
     *
     * @return 委托列表
     */
    List<DelegationDO> getDelegationsNotAccepted();

    /**
     * 获取软件项目委托测试申请表
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getDelegationTable2(String id);

    /**
     * 获取委托测试软件功能列表
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getDelegationTable3(String id);

    /**
     * 获取软件文档评审表
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getDelegationTable14(String id);

    /**
     * 获取软件项目委托测试工作检查表
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getDelegationTable12(String id);

    /**
     * 获取报价单
     *
     * @param id 表格编号
     * @return 报价单内容
     */
    JSONObject getOffer(String id);

    /**
     * 获得委托列表
     *
     * @param ids 编号
     * @return 委托列表
     */
    List<DelegationDO> getDelegationList(Collection<Long> ids);

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

    /**
     * 获得委托流程列表
     * @param id 委托id
     * @return 委托流程列表
     */
    List<FlowLogDO> getDelegationProcessList(Long id);

    /**
     * 客户取消委托
     *
     * @param delegationCancelReqVO 取消信息
     */
    void cancelDelegationClient(DelegationCancelReqVO delegationCancelReqVO);

    /**
     * 管理员取消委托
     *
     * @param delegationCancelReqVO 取消信息
     */
    void cancelDelegationAdmin(DelegationCancelReqVO delegationCancelReqVO);

    /**
     * 导出委托表格
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格2
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable2(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格3
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable3(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格12
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable12(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格14
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable14(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托offer
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportOffer(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格4
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable4(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格5
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable5(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格6
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable6(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格13
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable13(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格7
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable7(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格8
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable8(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格9
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable9(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格10
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable10(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 导出委托表格11
     *
     * @param exportTableReqVO 导出信息
     * @return pdf文件链接
     */
    String exportTable11(DelegationExportTableReqVO exportTableReqVO) throws IOException;

    /**
     * 为表格生成pdf
     * @param pdfRequestVO 生成条件
     * @return pdf文件链接
     */
    String exportPDFOfTable(PDFRequestVO pdfRequestVO) throws IOException;
}
