package cn.iocoder.yudao.module.system.convert.auth;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.yudao.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.module.system.api.social.dto.SocialUserUnbindReqDTO;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth.*;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserCreateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.permission.MenuIdEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mapping(source = "updateTime", target = "updateTime", ignore = true) // 字段相同，但是含义不同，忽略
    LoginUser convert0(AdminUserDO bean);

    default LoginUser convert(AdminUserDO bean) {
        // 目的，为了设置 UserTypeEnum.ADMIN.getValue()
        return convert0(bean).setUserType(UserTypeEnum.ADMIN.getValue());
    }

    default AuthPermissionInfoRespVO convert(AdminUserDO user, List<RoleDO> roleList, List<MenuDO> menuList) {
        return AuthPermissionInfoRespVO.builder()
            .user(AuthPermissionInfoRespVO.UserVO.builder().id(user.getId()).nickname(user.getNickname()).avatar(user.getAvatar()).build())
            .roles(CollectionUtils.convertSet(roleList, RoleDO::getCode))
            .permissions(CollectionUtils.convertSet(menuList, MenuDO::getPermission))
            .build();
    }

    AuthMenuRespVO convertTreeNode(MenuDO menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthMenuRespVO> buildMenuTree(List<MenuDO> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(MenuDO::getSort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(MenuIdEnum.ROOT.getId())).forEach(childNode -> {
            // 获得父节点
            AuthMenuRespVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                    childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return CollectionUtils.filterList(treeNodeMap.values(), node -> MenuIdEnum.ROOT.getId().equals(node.getParentId()));
    }

//    default AuthSimpleMenuRespVO convert(MenuDO menu) {
//        return AuthSimpleMenuRespVO.builder()
//                .path(menu.getPath())
//                .name(menu.getName())
//                .hideInMenu(Objects.equals(menu.getStatus(), CommonStatusEnum.DISABLE.getStatus()))
//                .build();
//    }

//    default List<AuthSimpleMenuRespVO> convert(List<MenuDO> menuList) {
//        Map<MenuDO, AuthSimpleMenuRespVO> map = new HashMap<>();
//        for (MenuDO menu : menuList) {
//            ArrayList<String> paths = new ArrayList<>();
//            MenuDO cur = menu;
//            while (cur != null) {
//                if (map.containsKey(cur)) {
//                    paths.add(map.get(cur).getPath());
//                    break;
//                }
//                paths.add(cur.getPath());
//                MenuDO finalCur = cur;
//                cur = menuList.stream().filter(m -> m.getId().equals(finalCur.getParentId())).findFirst().orElse(null);
//            }
//            // 反转paths
//            Collections.reverse(paths);
//            assert menu != null;
//            map.put(menu, AuthSimpleMenuRespVO.builder()
//                    .path(String.join("/", paths))
//                    .name(menu.getName())
//                    .hideInMenu(Objects.equals(menu.getStatus(), CommonStatusEnum.DISABLE.getStatus()))
//                    .build());
//        }
//        // 对map的value集合进行排序
//        return map.values().stream().sorted(Comparator.comparing(AuthSimpleMenuRespVO::getPath)).collect(Collectors.toList());
//    }

    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialBindReqVO reqVO);
    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLogin2ReqVO reqVO);
    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AuthSocialUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AuthSendSmsReqVO reqVO);
    SmsCodeUseReqDTO convert(AuthResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp);
    SmsCodeUseReqDTO convert(AuthRegisterReqVO reqVO, Integer scene, String usedIp);
    UserCreateReqVO convert(AuthRegisterReqVO reqVO);

    default AuthSimpleMenuRespVO convert(FrontMenuDO bean) {
        AuthSimpleMenuRespVO menu = new AuthSimpleMenuRespVO();
        menu.setPath(bean.getPath());
        menu.setName(bean.getName());
        menu.setHideInMenu(bean.getHideInMenu());
        try {
            menu.setParentKeys(OBJECT_MAPPER.readValue(bean.getParentKeys(), List.class));
        } catch (Exception e) {

        }
        return menu;
    }

    default List<AuthSimpleMenuRespVO> convertList(List<FrontMenuDO> menus) {
        List<AuthSimpleMenuRespVO> list = new ArrayList<>();
        menus.forEach(menu -> list.add(AuthConvert.INSTANCE.convert(menu)));
        return list;
    }

}
