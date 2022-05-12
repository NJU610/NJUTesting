package cn.iocoder.yudao.module.system.service.sample;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.sample.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.sample.SampleDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 样品 Service 接口
 *
 * @author lyw
 */
public interface SampleService {

    /**
     * 创建样品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSample(@Valid SampleCreateReqVO createReqVO);

    /**
     * 更新样品
     *
     * @param updateReqVO 更新信息
     */
    void updateSample(@Valid SampleUpdateReqVO updateReqVO);

    /**
     * 提交样品
     *
     * @param submitReqVO 提交信息
     */
    void submitSample(@Valid SampleSubmitReqVO submitReqVO);

    /**
     * 样品验收通过
     *
     * @param auditReqVO 验收信息
     */
    void auditSampleSuccess(@Valid SampleAuditReqVO auditReqVO);

    /**
     * 样品验收不通过，用户重新发送样品中
     *
     * @param auditReqVO 验收信息
     */
    void auditSampleFailResend(@Valid SampleAuditReqVO auditReqVO);

    /**
     * 样品验收不通过，用户修改样品信息中
     *
     * @param auditReqVO 验收信息
     */
    void auditSampleFailModify(@Valid SampleAuditReqVO auditReqVO);

    /**
     * 删除样品
     *
     * @param id 编号
     */
    void deleteSample(Long id);

    /**
     * 获得样品
     *
     * @param id 编号
     * @return 样品
     */
    SampleDO getSample(Long id);

    /**
     * 获得样品列表
     *
     * @param ids 编号
     * @return 样品列表
     */
    List<SampleDO> getSampleList(Collection<Long> ids);

    /**
     * 获得样品分页
     *
     * @param pageReqVO 分页查询
     * @return 样品分页
     */
    PageResult<SampleDO> getSamplePage(SamplePageReqVO pageReqVO);

    /**
     * 获得样品列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 样品列表
     */
    List<SampleDO> getSampleList(SampleExportReqVO exportReqVO);

}
