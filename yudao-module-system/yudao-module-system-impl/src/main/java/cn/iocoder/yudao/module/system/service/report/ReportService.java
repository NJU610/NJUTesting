package cn.iocoder.yudao.module.system.service.report;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import com.alibaba.fastjson.JSONObject;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 测试报告 Service 接口
 *
 * @author lyw
 */
public interface ReportService {

    /**
     * 创建测试报告
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReport(@Valid ReportCreateReqVO createReqVO);

    /**
     * 保存软件测试报告
     *
     * @param saveReqVO 填写信息
     */
    void saveReportTable7(@Valid ReportSaveTableReqVO saveReqVO);

    /**
     * 保存测试用例（电子记录）
     *
     * @param saveReqVO 填写信息
     */
    void saveReportTable8(@Valid ReportSaveTableReqVO saveReqVO);

    /**
     * 保存软件测试记录（电子记录）
     *
     * @param saveReqVO 填写信息
     */
    void saveReportTable9(@Valid ReportSaveTableReqVO saveReqVO);

    /**
     * 保存测试报告检查表
     *
     * @param saveReqVO 填写信息
     */
    void saveReportTable10(@Valid ReportSaveTableReqVO saveReqVO);

    /**
     * 保存软件测试问题清单（电子记录）
     *
     * @param saveReqVO 填写信息
     */
    void saveReportTable11(@Valid ReportSaveTableReqVO saveReqVO);

    /**
     * 提交测试报告
     *
     * @param submitReqVO 提交信息
     */
    void submitReport(@Valid ReportSubmitReqVO submitReqVO);

    /**
     * 测试部主管审核测试报告通过
     *
     * @param acceptReqVO 审核信息
     */
    void acceptReportManager(@Valid ReportAcceptReqVO acceptReqVO);
    //以项目为单位
    void acceptReportManagerByProject(@Valid ReportAcceptReqVO acceptReqVO);
    /**
     * 测试部主管审核测试报告不通过
     *
     * @param rejectReqVO 审核信息
     */
    void rejectReportManager(@Valid ReportRejectReqVO rejectReqVO);
    //以项目为单位
    void rejectReportManagerByProject(@Valid ReportRejectReqVO rejectReqVO);
    /**
     * 客户审核测试报告通过
     *
     * @param acceptReqVO 审核信息
     */
    void acceptReportClient(@Valid ReportAcceptReqVO acceptReqVO);
    //以项目为单位
    void acceptReportClientByProject(@Valid ReportAcceptReqVO acceptReqVO);
    /**
     * 客户审核测试报告不通过
     *
     * @param rejectReqVO 审核信息
     */
    void rejectReportClient(@Valid ReportRejectReqVO rejectReqVO);
    //以项目为单位
    void rejectReportClientByProject(@Valid ReportRejectReqVO rejectReqVO);

    /**
     * 授权签字人审核测试报告通过
     *
     * @param acceptReqVO 审核信息
     */
    void acceptReportSignatory(@Valid ReportAcceptReqVO acceptReqVO);
    //以项目为单位
    void acceptReportSignatoryByProject(@Valid ReportAcceptReqVO acceptReqVO);

    /**
     * 授权签字人审核测试报告不通过
     *
     * @param rejectReqVO 审核信息
     */
    void rejectReportSignatory(@Valid ReportRejectReqVO rejectReqVO);
    //以项目为单位
    void rejectReportSignatoryByProject(@Valid ReportRejectReqVO rejectReqVO);
    /**
     * 测试部测试文档归档
     *
     * @param archiveReqVO 归档信息
     */
    void archiveReport(@Valid ReportArchiveReqVO archiveReqVO);

    /**
     * 市场部发送测试报告
     *
     * @param sendReqVO 发送信息
     */
    void sendReport(@Valid ReportSendReqVO sendReqVO);

    /**
     * 客户确认接收测试报告
     *
     * @param receiveReqVO 接收信息
     */
    void receiveReport(@Valid ReportReceiveReqVO receiveReqVO);

    /**
     * 删除测试报告
     *
     * @param id 编号
     */
    void deleteReport(Long id);

    /**
     * 获得测试报告
     *
     * @param id 编号
     * @return 测试报告
     */
    ReportDO getReport(Long id);

    /**
     * 获得测试报告表单
     *
     * @param tableName 表名
     * @param tableId 编号
     */
    JSONObject getReportTable(String tableName, String tableId);

    /**
     * 获得测试报告列表
     *
     * @param ids 编号
     * @return 测试报告列表
     */
    List<ReportDO> getReportList(Collection<Long> ids);

    /**
     * 获得测试报告分页
     *
     * @param pageReqVO 分页查询
     * @return 测试报告分页
     */
    PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO);

    /**
     * 获得测试报告列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 测试报告列表
     */
    List<ReportDO> getReportList(ReportExportReqVO exportReqVO);

}
