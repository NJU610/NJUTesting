package cn.iocoder.yudao.module.system.dal.mysql.project;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.company.UserCompanyDO;
import cn.iocoder.yudao.module.system.dal.dataobject.project.ProjectDO;
import cn.iocoder.yudao.module.system.dal.dataobject.report.ReportDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.report.vo.*;

/**
 * 项目 Mapper
 *
 * @author zcq
 */
public interface ProjectMapper extends BaseMapperX<ProjectDO>{

    default boolean existsByDelegation(Long delegationId) {
        return selectCount(new LambdaQueryWrapperX<ProjectDO>()
                .eq(ProjectDO::getDelegationId,
                        delegationId)) > 0;
    }

    default ProjectDO selectByDelegation(Long delegationId) {
        return selectOne(new LambdaQueryWrapperX<ProjectDO>()
                .eq(ProjectDO::getDelegationId, delegationId));
    }
}
