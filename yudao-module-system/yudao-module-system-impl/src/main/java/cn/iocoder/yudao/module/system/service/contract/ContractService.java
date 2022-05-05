package cn.iocoder.yudao.module.system.service.contract;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 合同 Service 接口
 *
 * @author lyw
 */
public interface ContractService {

    /**
     * 创建合同
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createContract(@Valid ContractCreateReqVO createReqVO);

    /**
     * 创建合同
     *
     * @param delegationId 委托编号
     * @param creatorId 发起者编号
     * @return 编号
     */
    Long createContract(Long delegationId, Long creatorId);

    /**
     * 保存软件委托测试合同
     *
     * @param saveReqVO 填写信息
     */
    void saveContractTable4(@Valid ContractSaveTableReqVO saveReqVO);

    /**
     * 保存软件项目委托测试保密协议
     *
     * @param saveReqVO 填写信息
     */
    void saveContractTable5(@Valid ContractSaveTableReqVO saveReqVO);

    /**
     * 市场部测试人员提交合同
     *
     * @param submitReqVO 提交信息
     */
    void submitContractStaff(@Valid ContractSubmitReqVO submitReqVO);

    /**
     * 客户提交合同
     *
     * @param submitReqVO 提交信息
     */
    void submitContractClient(@Valid ContractSubmitReqVO submitReqVO);

    /**
     * 更新合同
     *
     * @param updateReqVO 更新信息
     */
    void updateContract(@Valid ContractUpdateReqVO updateReqVO);

    /**
     * 删除合同
     *
     * @param id 编号
     */
    void deleteContract(Long id);

    /**
     * 获得合同
     *
     * @param id 编号
     * @return 合同
     */
    ContractDO getContract(Long id);

    /**
     * 获得合同列表
     *
     * @param ids 编号
     * @return 合同列表
     */
    List<ContractDO> getContractList(Collection<Long> ids);

    /**
     * 获得合同分页
     *
     * @param pageReqVO 分页查询
     * @return 合同分页
     */
    PageResult<ContractDO> getContractPage(ContractPageReqVO pageReqVO);

    /**
     * 获得合同列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 合同列表
     */
    List<ContractDO> getContractList(ContractExportReqVO exportReqVO);

}
