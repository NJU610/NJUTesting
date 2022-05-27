package cn.iocoder.yudao.module.system.enums.permission;

import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    TENANT_ADMIN("tenant_admin", "租户管理员"),
    NORMAL_USER("normal_user", "普通用户"),
    CUSTOMER("customer", "客户"),
    TEST_DEPARTMENT_MANAGER("test_department_manager", "测试部主管"),
    TEST_DEPARTMENT_STAFF("test_department_staff", "测试部员工"),
    MARKET_DEPARTMENT_MANAGER("marketing_department_manger", "市场部主管"),
    MARKET_DEPARTMENT_STAFF("marketing_department_staff", "市场部员工"),
    QUALITY_DEPARTMENT_STAFF("quality_department_staff", "质量部员工"),
    SIGNATORY("signatory", "签字人"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }

}
