package cn.iocoder.yudao.module.system.service.delegation;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.controller.admin.delegation.vo.*;
import cn.iocoder.yudao.module.system.convert.delegation.DelegationConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.delegation.DelegationDO;
import cn.iocoder.yudao.module.system.dal.mongo.delegation.CommonObject;
import cn.iocoder.yudao.module.system.dal.mysql.delegation.DelegationMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DELEGATION_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DELEGATION_TABLE_NOT_EXISTS;

/**
 * 委托 Service 实现类
 *
 * @author lyw
 */
@Service
@Validated
public class DelegationServiceImpl implements DelegationService {

    @Resource
    private DelegationMapper delegationMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Long createDelegation(DelegationCreateReqVO createReqVO) {
        // 插入
        DelegationDO delegation = DelegationConvert.INSTANCE.convert(createReqVO);
        delegationMapper.insert(delegation);
        // 返回
        return delegation.getId();
    }

    @Override
    public String createDelegationTable(DelegationCreateTableReqVo createTableReqVo) {
        // 校验存在
        Long delegationId = createTableReqVo.getDelegationId();
        this.validateDelegationExists(delegationId);

        String tableName = createTableReqVo.getTableName();
        Map<String, Object> data = createTableReqVo.getData();

        CommonObject obj = new CommonObject();
        obj.setId(null);
        mongoTemplate.insert(obj, tableName);

        if (data != null && !data.isEmpty()) {
            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("_id").is(obj.getId())
            ));
            Update update = new Update();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                update.set(entry.getKey(), entry.getValue());
            }
            mongoTemplate.upsert(query, update, tableName);
        }

        DelegationDO delegation = new DelegationDO();
        delegation.setId(delegationId);
        if (tableName.equals("table2")) {
            delegation.setTable2Id(obj.getId());
        } else if (tableName.equals("table3")) {
            delegation.setTable3Id(obj.getId());
        }
        delegationMapper.updateById(delegation);

        return obj.getId();
    }

    @Override
    public void updateDelegation(DelegationUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDelegationExists(updateReqVO.getId());
        // 更新
        DelegationDO updateObj = DelegationConvert.INSTANCE.convert(updateReqVO);
        delegationMapper.updateById(updateObj);
    }

    @Override
    public void updateDelegationTable(DelegationUpdateTableReqVo updateReqVO) {
        // 校验存在
        String tableId = updateReqVO.getId();
        String tableName = updateReqVO.getTableName();
        this.validateDelegationTableExists(tableId, tableName);

        Map<String, Object> data = updateReqVO.getData();

        if (data != null && !data.isEmpty()) {
            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("_id").is(tableId)
            ));
            Update update = new Update();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                update.set(entry.getKey(), entry.getValue());
            }
            mongoTemplate.upsert(query, update, tableName);
        }

    }

    @Override
    public void deleteDelegation(Long id) {
        // 校验存在
        this.validateDelegationExists(id);

        // 删除表格
        DelegationDO delegationDO = delegationMapper.selectById(id);
        if (delegationDO.getTable2Id() != null) {
            deleteTable(delegationDO.getTable2Id(), "table2");
        }
        if (delegationDO.getTable3Id() != null) {
            deleteTable(delegationDO.getTable3Id(), "table3");
        }

        // 删除
        delegationMapper.deleteById(id);
    }

    private void deleteTable(String tableId, String tableName) {
        // 校验存在
        this.validateDelegationTableExists(tableId, tableName);
        // 删除
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("_id").is(tableId)
        ));
        Update update = new Update();
        update.set("deleted", true);
        mongoTemplate.updateFirst(query, update, tableName);
    }

    private void validateDelegationTableExists(String tableId, String tableName) {
        CommonObject obj = mongoTemplate.findById(tableId, CommonObject.class, tableName);
        if (obj == null || obj.getDeleted()) {
            throw exception(DELEGATION_TABLE_NOT_EXISTS);
        }
    }

    private void validateDelegationExists(Long id) {
        if (delegationMapper.selectById(id) == null) {
            throw exception(DELEGATION_NOT_EXISTS);
        }
    }

    @Override
    public DelegationDO getDelegation(Long id) {
        return delegationMapper.selectById(id);
    }

    @Override
    public List<DelegationDO> getDelegationsByCurrentUser() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        return delegationMapper.selectList("creator_id", loginUserId);
    }

    @Override
    public List<DelegationDO> getDelegationsByCreator(Long creatorId) {
        return delegationMapper.selectList("creator_id", creatorId);
    }

    @Override
    public List<DelegationDO> getDelegationsNotAccepted() {
        QueryWrapperX<DelegationDO> queryWrapper = new QueryWrapperX<>();
        queryWrapper.isNull("acceptor_id");
        return delegationMapper.selectList(queryWrapper);
    }

    @Override
    public String getDelegationTable(String id, String tableName) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        return mongoTemplate.findOne(query, String.class, tableName);
    }

    @Override
    public List<DelegationDO> getDelegationList(Collection<Long> ids) {
        return delegationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DelegationDO> getDelegationPage(DelegationPageReqVO pageReqVO) {
        return delegationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DelegationDO> getDelegationList(DelegationExportReqVO exportReqVO) {
        return delegationMapper.selectList(exportReqVO);
    }

}
