package cn.iocoder.yudao.module.system.dal.dataobject.permission;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 前台菜单 DO
 *
 * @author qjy
 */
@TableName("system_front_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontMenuDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 路由路径
     */
    private String path;
    /**
     * 是否隐藏（0为否，1为是）
     */
    private Integer hideInMenu;
    /**
     * 菜单状态（0正常 1停用）
     */
    private Integer status;
    /**
     * 关联数组
     */
    private String parentKeys;

}
