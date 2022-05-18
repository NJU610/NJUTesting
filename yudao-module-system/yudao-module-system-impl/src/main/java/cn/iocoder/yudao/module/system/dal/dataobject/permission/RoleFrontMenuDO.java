package cn.iocoder.yudao.module.system.dal.dataobject.permission;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 角色前台菜单 DO
 *
 * @author qjy
 */
@TableName("system_role_front_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFrontMenuDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 菜单编号
     */
    private Long frontMenuId;

}
