package cn.iocoder.yudao.module.system.enums.sms;

import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户短信验证码发送场景的枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum SmsSceneEnum implements IntArrayValuable {

    MEMBER_LOGIN(1, "user-sms-login", "手机号登陆"),
    MEMBER_UPDATE_MOBILE(2, "user-sms-reset-password", "修改手机"),
    MEMBER_FORGET_PASSWORD(3, "user-sms-update-mobile", "忘记密码"),
    ADMIN_LOGIN(4, "admin-user-sms-login", "手机号登陆"),
    ADMIN_FORGET_PASSWORD(5, "admin-sms-reset-password", "忘记密码"),
    ADMIN_REGISTER(6, "admin-register", "注册");

    // 如果未来希望管理后台支持手机验证码登陆，可以通过添加 ADMIN_MEMBER_LOGIN 枚举

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SmsSceneEnum::getScene).toArray();

    /**
     * 验证场景的编号
     */
    private final Integer scene;
    /**
     * 模版编码
     */
    private final String templateCode;
    /**
     * 描述
     */
    private final String description;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static SmsSceneEnum getCodeByScene(Integer scene) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
                values());
    }

}
