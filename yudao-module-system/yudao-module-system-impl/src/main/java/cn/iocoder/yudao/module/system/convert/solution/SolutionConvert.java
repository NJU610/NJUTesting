package cn.iocoder.yudao.module.system.convert.solution;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.system.controller.admin.solution.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.solution.SolutionDO;

/**
 * 测试方案 Convert
 *
 * @author lyw
 */
@Mapper
public interface SolutionConvert {

    SolutionConvert INSTANCE = Mappers.getMapper(SolutionConvert.class);

    SolutionDO convert(SolutionCreateReqVO bean);

    SolutionDO convert(SolutionUpdateReqVO bean);

    SolutionRespVO convert(SolutionDO bean);

    List<SolutionRespVO> convertList(List<SolutionDO> list);

    PageResult<SolutionRespVO> convertPage(PageResult<SolutionDO> page);

    List<SolutionExcelVO> convertList02(List<SolutionDO> list);

}
