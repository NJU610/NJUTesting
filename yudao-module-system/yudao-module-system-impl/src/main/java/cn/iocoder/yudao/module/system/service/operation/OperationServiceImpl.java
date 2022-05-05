package cn.iocoder.yudao.module.system.service.operation;

import cn.iocoder.yudao.module.system.dal.mysql.operation.OperationMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 流程操作 Service 实现类
 *
 * @author qjy
 */
@Service
@Validated
public class OperationServiceImpl implements OperationService {

    @Resource
    private OperationMapper operationMapper;



}
