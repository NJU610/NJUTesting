package cn.iocoder.yudao.module.system.service.report;

import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.table.TableMongoRepository;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import cn.iocoder.yudao.module.system.enums.delegation.DelegationStateEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.system.convert.report.ReportConvert;
import cn.iocoder.yudao.module.system.dal.mysql.report.ReportMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 测试报告 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private TableMongoRepository tableMongoRepository;

    @Override
    public Long createReport(ReportCreateReqVO createReqVO) {
        // 校验委托存在和状态
        Long delegationId = createReqVO.getDelegationId();
        DelegationDO delegation = delegationMapper
                .validateDelegationState(delegationId, DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT);
        ReportDO report = ReportConvert.INSTANCE.convert(createReqVO);
        reportMapper.insert(report);
        Long reportId = report.getId();
        // 更新委托
        delegation.setReportId(reportId);
        delegationMapper.updateById(delegation);
        // 返回
        return reportId;
    }

    @Override
    public void saveReportTable7(ReportSaveTableReqVO saveReqVO) {
        // 校验报告是否存在
        Long reportId = saveReqVO.getReportId();
        ReportDO report = this.validateReportExists(reportId);
        // 保存表单
        if (report.getTable7Id() == null) {
            report.setTable7Id(tableMongoRepository.create("table7", saveReqVO.getData()));
            reportMapper.updateById(report);
        } else {
            tableMongoRepository.upsert("table7", report.getTable7Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveReportTable8(ReportSaveTableReqVO saveReqVO) {
        // 校验报告是否存在
        Long reportId = saveReqVO.getReportId();
        ReportDO report = this.validateReportExists(reportId);
        // 保存表单
        if (report.getTable8Id() == null) {
            report.setTable8Id(tableMongoRepository.create("table8", saveReqVO.getData()));
            reportMapper.updateById(report);
        } else {
            tableMongoRepository.upsert("table8", report.getTable8Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveReportTable9(ReportSaveTableReqVO saveReqVO) {
        // 校验报告是否存在
        Long reportId = saveReqVO.getReportId();
        ReportDO report = this.validateReportExists(reportId);
        // 保存表单
        if (report.getTable9Id() == null) {
            report.setTable9Id(tableMongoRepository.create("table9", saveReqVO.getData()));
            reportMapper.updateById(report);
        } else {
            tableMongoRepository.upsert("table9", report.getTable9Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveReportTable10(ReportSaveTableReqVO saveReqVO) {
        // 校验报告是否存在
        Long reportId = saveReqVO.getReportId();
        ReportDO report = this.validateReportExists(reportId);
        // 保存表单
        if (report.getTable10Id() == null) {
            report.setTable10Id(tableMongoRepository.create("table10", saveReqVO.getData()));
            reportMapper.updateById(report);
        } else {
            tableMongoRepository.upsert("table10", report.getTable10Id(), saveReqVO.getData());
        }
    }

    @Override
    public void saveReportTable11(ReportSaveTableReqVO saveReqVO) {
        // 校验报告是否存在
        Long reportId = saveReqVO.getReportId();
        ReportDO report = this.validateReportExists(reportId);
        // 保存表单
        if (report.getTable11Id() == null) {
            report.setTable11Id(tableMongoRepository.create("table11", saveReqVO.getData()));
            reportMapper.updateById(report);
        } else {
            tableMongoRepository.upsert("table11", report.getTable11Id(), saveReqVO.getData());
        }
    }

    @Override
    public void submitReport(ReportSubmitReqVO submitReqVO) {
        // 校验报告是否存在
        Long reportId = submitReqVO.getId();
        ReportDO report = this.validateReportExists(reportId);
        // 校验状态
        if (report.getTable7Id() == null ||
                report.getTable8Id() == null ||
                report.getTable9Id() == null ||
                report.getTable11Id() == null) {
            throw exception(REPORT_TABLE_NOT_FILLED);
        }
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.TESTING_DEPT_WRITING_TEST_REPORT,
                DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_FAIL,
                DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_FAIL,
                DelegationStateEnum.SIGNATORY_AUDIT_TEST_REPORT_FAIL);
        // 更新状态
        delegation.setState(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void acceptReportManager(ReportAcceptReqVO acceptReqVO) {
        // 审核报告
        Long reportId = acceptReqVO.getId();
        String remark = acceptReqVO.getRemark();
        DelegationDO delegation = this.auditReportManager(reportId, remark);
        // 更新状态
        delegation.setState(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void rejectReportManager(ReportRejectReqVO rejectReqVO) {
        // 审核报告
        Long reportId = rejectReqVO.getId();
        String remark = rejectReqVO.getRemark();
        DelegationDO delegation = this.auditReportManager(reportId, remark);
        // 更新状态
        delegation.setState(DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_FAIL.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void acceptReportClient(ReportAcceptReqVO acceptReqVO) {
        // 审核报告
        Long reportId = acceptReqVO.getId();
        String remark = acceptReqVO.getRemark();
        DelegationDO delegation = this.auditReportClient(reportId, remark);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void rejectReportClient(ReportRejectReqVO rejectReqVO) {
        // 审核报告
        Long reportId = rejectReqVO.getId();
        String remark = rejectReqVO.getRemark();
        DelegationDO delegation = this.auditReportClient(reportId, remark);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_FAIL.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void acceptReportSignatory(ReportAcceptReqVO acceptReqVO) {
        // 审核报告
        Long reportId = acceptReqVO.getId();
        String remark = acceptReqVO.getRemark();
        DelegationDO delegation = this.auditReportSignatory(reportId, remark);
        // 连续更新状态
        delegation.setState(DelegationStateEnum.SIGNATORY_AUDIT_TEST_REPORT_SUCCESS.getState());
        delegation.setState(DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void rejectReportSignatory(ReportRejectReqVO rejectReqVO) {
        // 审核报告
        Long reportId = rejectReqVO.getId();
        String remark = rejectReqVO.getRemark();
        DelegationDO delegation = this.auditReportSignatory(reportId, remark);
        // 更新状态
        delegation.setState(DelegationStateEnum.SIGNATORY_AUDIT_TEST_REPORT_FAIL.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void archiveReport(ReportArchiveReqVO archiveReqVO) {
        // 校验报告是否存在
        Long reportId = archiveReqVO.getId();
        ReportDO report = this.validateReportExists(reportId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE);
        // 更新状态
        delegation.setState(DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void sendReport(ReportSendReqVO sendReqVO) {
        // 校验报告是否存在
        Long reportId = sendReqVO.getId();
        this.validateReportExists(reportId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.MARKETING_DEPT_SEND_TEST_REPORT);
        // 更新状态
        delegation.setState(DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void receiveReport(ReportReceiveReqVO receiveReqVO) {
        // 校验报告是否存在
        Long reportId = receiveReqVO.getId();
        this.validateReportExists(reportId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT);
        // 更新状态
        delegation.setState(DelegationStateEnum.CLIENT_CONFIRM_RECEIVE_TEST_REPORT.getState());
        delegationMapper.updateById(delegation);
        // TODO 保存日志
    }

    @Override
    public void deleteReport(Long id) {
        // 校验存在
        this.validateReportExists(id);
        // 删除
        reportMapper.deleteById(id);
    }

    private DelegationDO auditReportManager(Long reportId, String remark) {
        // 校验报告是否存在
        ReportDO report = this.validateReportExists(reportId);
        // 校验状态
        if (report.getTable10Id() == null) {
            throw exception(REPORT_TABLE_NOT_FILLED);
        }
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT);
        // 更新意见
        if (remark != null) {
            report.setManagerRemark(remark);
            reportMapper.updateById(report);
        }
        return delegation;
    }

    private DelegationDO auditReportClient(Long reportId, String remark) {
        // 校验报告是否存在
        ReportDO report = this.validateReportExists(reportId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS);
        // 更新意见
        if (remark != null) {
            report.setClientRemark(remark);
            reportMapper.updateById(report);
        }
        return delegation;
    }

    private DelegationDO auditReportSignatory(Long reportId, String remark) {
        // 校验报告是否存在
        ReportDO report = this.validateReportExists(reportId);
        // 校验状态
        DelegationDO delegation = delegationMapper.validateDelegationStateByReport(reportId,
                DelegationStateEnum.CLIENT_AUDIT_TEST_REPORT_SUCCESS);
        // 更新意见
        if (remark != null) {
            report.setSignatoryRemark(remark);
            reportMapper.updateById(report);
        }
        return delegation;
    }

    private ReportDO validateReportExists(Long id) {
        ReportDO report = reportMapper.selectById(id);
        if (report == null) {
            throw exception(REPORT_NOT_EXISTS);
        }
        return report;
    }

    @Override
    public ReportDO getReport(Long id) {
        return reportMapper.selectById(id);
    }

    @Override
    public String getReportTable(String tableName, String tableId) {
        return tableMongoRepository.get(tableName, tableId);
    }

    @Override
    public List<ReportDO> getReportList(Collection<Long> ids) {
        return reportMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ReportDO> getReportPage(ReportPageReqVO pageReqVO) {
        return reportMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReportDO> getReportList(ReportExportReqVO exportReqVO) {
        return reportMapper.selectList(exportReqVO);
    }

}
