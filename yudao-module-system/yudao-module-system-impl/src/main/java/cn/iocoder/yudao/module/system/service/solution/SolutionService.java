package cn.iocoder.yudao.module.system.service.solution;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.system.controller.admin.contract.vo.ContractSaveTableReqVO;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.alibaba.fastjson.JSONObject;

/**
 * 测试方案 Service 接口
 *
 * @author lyw
 */
public interface SolutionService {

    /**
     * 获取软件测试方案表格
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getSolutionTable6(String id);

    /**
     * 获取测试方案评审表
     *
     * @param id 表格编号
     * @return 表格内容
     */
    JSONObject getSolutionTable13(String id);

    /**
     * 更新测试方案
     *
     * @param updateReqVO 更新信息
     */
    void updateSolution(@Valid SolutionUpdateReqVO updateReqVO);

    /**
     * 删除测试方案
     *
     * @param id 编号
     */
    void deleteSolution(Long id);

    /**
     * 获得测试方案
     *
     * @param id 编号
     * @return 测试方案
     */
    SolutionDO getSolution(Long id);

    /**
     * 获得测试方案列表
     *
     * @param ids 编号
     * @return 测试方案列表
     */
    List<SolutionDO> getSolutionList(Collection<Long> ids);

    /**
     * 获得测试方案分页
     *
     * @param pageReqVO 分页查询
     * @return 测试方案分页
     */
    PageResult<SolutionDO> getSolutionPage(SolutionPageReqVO pageReqVO);

    /**
     * 获得测试方案列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 测试方案列表
     */
    List<SolutionDO> getSolutionList(SolutionExportReqVO exportReqVO);

}
