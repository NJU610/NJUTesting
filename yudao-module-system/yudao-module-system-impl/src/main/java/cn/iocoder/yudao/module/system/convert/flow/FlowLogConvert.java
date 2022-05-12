package cn.iocoder.yudao.module.system.convert.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.module.system.controller.admin.flow.vo.FlowLogInstanceResponseVO;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowLogDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static cn.hutool.core.bean.BeanUtil.copyProperties;

/**
 *  Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FlowLogConvert {

    FlowLogConvert INSTANCE = Mappers.getMapper(FlowLogConvert.class);

    default List<FlowLogInstanceResponseVO> convertList(List<FlowLogDO> list) {
        return list.stream().map(this::convert).collect(java.util.stream.Collectors.toList());
    }

    default FlowLogInstanceResponseVO convert(FlowLogDO flowLogDO) {
        try {
            return BeanUtil
                    .copyProperties(flowLogDO, FlowLogInstanceResponseVO.class, "mapValue")
                    .setMapValue(new ObjectMapper().readValue(flowLogDO.getMapValue(), new TypeReference<Map<String,Object>>() {}));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    default List<String> convertListToString(List<FlowLogDO> list) {
        return list.stream().map(FlowLogDO::getRemark).collect(java.util.stream.Collectors.toList());
    }
}
