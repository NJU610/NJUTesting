package cn.iocoder.yudao.module.system.dal.dataobject.table;

import lombok.Data;

@Data
public class TableMongoBaseDO {

    private String id;

    private Boolean deleted = false;

}
