package cn.iocoder.yudao.module.system.dal.mysql.flow;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import lombok.experimental.Delegate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 流程操作 Mapper
 *
 * @author qjy
 */
@Mapper
public interface FlowLogMapper extends BaseMapperX<FlowLogDO> {


}
