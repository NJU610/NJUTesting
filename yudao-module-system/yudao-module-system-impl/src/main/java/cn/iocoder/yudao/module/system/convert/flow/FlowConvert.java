package cn.iocoder.yudao.module.system.convert.flow;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;

/**
 * 项目流程 Convert
 *
 * @author lyw
 */
@Mapper
public interface FlowConvert {

    FlowConvert INSTANCE = Mappers.getMapper(FlowConvert.class);

    FlowDO convert(FlowCreateVO bean);

    FlowDO convert(FlowUpdateReqVO bean);

    FlowRespVO convert(FlowDO bean);

    List<FlowRespVO> convertList(List<FlowDO> list);

    PageResult<FlowRespVO> convertPage(PageResult<FlowDO> page);

    List<FlowExcelVO> convertList02(List<FlowDO> list);

}
