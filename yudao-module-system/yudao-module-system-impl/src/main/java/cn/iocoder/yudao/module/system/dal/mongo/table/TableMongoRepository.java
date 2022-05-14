package cn.iocoder.yudao.module.system.dal.mongo.table;

import cn.iocoder.yudao.module.system.dal.dataobject.table.TableMongoCreateDO;
import cn.iocoder.yudao.module.system.dal.dataobject.table.TableMongoQueryDO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DELEGATION_TABLE_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.TABLE_NOT_EXISTS;

@Repository
public class TableMongoRepository {

    @Resource
    private MongoTemplate mongoTemplate;

    public String create(String tableName, Map<String, Object> data) {
        TableMongoCreateDO obj = new TableMongoCreateDO();
        obj.setId(null);
        mongoTemplate.insert(obj, tableName);
        String tableId = obj.getId();

        upsert(tableName, tableId, data);

        return tableId;
    }

    public void upsert(String tableName, String tableId, Map<String, Object> data) {
        // 校验存在
        this.validateTableExists(tableName, tableId);
        // 更新
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

    public void delete(String tableName, String tableId) {
        // 校验存在
        this.validateTableExists(tableName, tableId);
        // 删除
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("_id").is(tableId)
        ));
        Update update = new Update();
        update.set("deleted", true);
        mongoTemplate.updateFirst(query, update, tableName);
    }

    public JSONObject get(String tableName, String tableId) {
        // 校验存在
        return this.validateTableExists(tableName, tableId);
    }

    private JSONObject validateTableExists(String tableName, String tableId) {
        JSONObject obj = mongoTemplate.findById(tableId, JSONObject.class, tableName);
        if (obj == null || obj.getBoolean("deleted")) {
            throw exception(TABLE_NOT_EXISTS);
        }
        return obj;
    }

}
