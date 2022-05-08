package cn.iocoder.yudao.module.system.dal.mysql.contract;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.contract.ContractDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.contract.vo.*;

/**
 * 合同 Mapper
 *
 * @author lyw
 */
@Mapper
public interface ContractMapper extends BaseMapperX<ContractDO> {

    default PageResult<ContractDO> selectPage(ContractPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ContractDO>()
                .eqIfPresent(ContractDO::getTable4Id, reqVO.getTable4Id())
                .eqIfPresent(ContractDO::getTable5Id, reqVO.getTable5Id())
                .eqIfPresent(ContractDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(ContractDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ContractDO::getId));
    }

    default List<ContractDO> selectList(ContractExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ContractDO>()
                .eqIfPresent(ContractDO::getTable4Id, reqVO.getTable4Id())
                .eqIfPresent(ContractDO::getTable5Id, reqVO.getTable5Id())
                .eqIfPresent(ContractDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(ContractDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ContractDO::getId));
    }

}
