package cn.iocoder.yudao.module.system.convert.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 *  Convert
 *
 * @author qjy
 */
@Mapper
public interface FlowLogConvert {

    FlowLogConvert INSTANCE = Mappers.getMapper(FlowLogConvert.class);

    default List<FlowLogInstanceResponseVO> convertInstanceList(List<FlowLogDO> list) {
        return list.stream().map(this::convertInstance).collect(java.util.stream.Collectors.toList());
    }

    default FlowLogInstanceResponseVO convertInstance(FlowLogDO flowLogDO) {
        try {
            return BeanUtil
                    .copyProperties(flowLogDO, FlowLogInstanceResponseVO.class, "mapValue")
                    .setMapValue(new ObjectMapper().readValue(flowLogDO.getMapValue(), new TypeReference<Map<String,Object>>() {}));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    FlowLogDO convert(FlowLogCreateReqVO bean);

    FlowLogDO convert(FlowLogUpdateReqVO bean);

    FlowLogRespVO convert(FlowLogDO bean);

    List<FlowLogRespVO> convertList(List<FlowLogDO> list);

    PageResult<FlowLogRespVO> convertPage(PageResult<FlowLogDO> page);

    List<FlowLogExcelVO> convertList02(List<FlowLogDO> list);

    default List<String> convertListToString(List<FlowLogDO> list) {
        return list.stream().map(FlowLogDO::getRemark).collect(java.util.stream.Collectors.toList());
    }

    default FlowLogSimpleResponseVO convertSimple(FlowLogDO list) {
        return BeanUtil.copyProperties(list, FlowLogSimpleResponseVO.class);
    }
    default List<FlowLogSimpleResponseVO> convertSimpleList(List<FlowLogDO> list){
        return list.stream().map(this::convertSimple).collect(java.util.stream.Collectors.toList());
    }
}
