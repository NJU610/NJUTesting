package cn.iocoder.yudao.module.system.service.auth;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.monitor.TracerUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.authentication.MultiUsernamePasswordAuthenticationToken;
import cn.iocoder.yudao.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.module.system.api.sms.SmsCodeApi;
import cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth.*;
import cn.iocoder.yudao.module.system.convert.auth.AuthConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.yudao.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.yudao.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsSceneEnum;
import cn.iocoder.yudao.module.system.service.common.CaptchaService;
import cn.iocoder.yudao.module.system.service.logger.LoginLogService;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.permission.RoleService;
import cn.iocoder.yudao.module.system.service.social.SocialUserService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;
import static java.util.Collections.singleton;

/**
 * Auth Service ?????????
 *
 * @author ????????????
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    @Lazy // ????????????????????????????????????????????????
    private AuthenticationManager authenticationManager;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection") // UserService ????????????
    private AdminUserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private UserSessionService userSessionService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private RoleService roleService;

    @Resource
    private Validator validator;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ?????? username ????????? AdminUserDO
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // ?????? LoginUser ??????
        return this.buildLoginUser(user);
    }

    @Override
    public LoginUser mockLogin(Long userId) {
        // ??????????????????????????? AdminUserDO
        AdminUserDO user = userService.getUser(userId);
        if (user == null) {
            throw new UsernameNotFoundException(String.valueOf(userId));
        }
        this.createLoginLog(user.getUsername(), LoginLogTypeEnum.LOGIN_MOCK, LoginResultEnum.SUCCESS);

        // ?????? LoginUser ??????
        return this.buildLoginUser(user);
    }

    @Override
    public String login(AuthLoginReqVO reqVO, String userIp, String userAgent) {
        // ???????????????????????????
        this.verifyCaptcha(reqVO);

        // ?????????????????????????????????
        LoginUser loginUser = this.login0(reqVO.getUsername(), reqVO.getPassword());

        // ????????????????????? Redis ???????????? sessionId ??????
        return createUserSessionAfterLoginSuccess(loginUser, LoginLogTypeEnum.LOGIN_USERNAME, userIp, userAgent);
    }

    @Override
    public String mobileLogin(AuthMobileLoginReqVO reqVO, String userIp, String userAgent) {
        AdminUserDO adminUserDO = userService.getUserByMobile(reqVO.getMobile());
        if (adminUserDO == null) {
            throw exception(AUTH_LOGIN_MOBILE_NOT_FOUND);
        }
        AuthLoginReqVO authLoginReqVO = new AuthLoginReqVO()
                .setUsername(adminUserDO.getUsername())
                .setPassword(reqVO.getPassword())
                .setCode(reqVO.getCode())
                .setUuid(reqVO.getUuid());
        return this.login(authLoginReqVO, userIp, userAgent);
    }

    private void verifyCaptcha(AuthLoginReqVO reqVO) {
        // ??????????????????????????????????????????
        if (!captchaService.isCaptchaEnable()) {
            return;
        }
        // ???????????????
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        // ??????????????????
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        String code = captchaService.getCaptchaCode(reqVO.getUuid());
        if (code == null) {
            // ????????????????????????????????????????????????
            this.createLoginLog(reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_NOT_FOUND);
            throw exception(AUTH_LOGIN_CAPTCHA_NOT_FOUND);
        }
        // ??????????????????
        if (!code.equals(reqVO.getCode())) {
            // ?????????????????????????????????????????????)
            this.createLoginLog(reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // ????????????????????????????????????
        captchaService.deleteCaptchaCode(reqVO.getUuid());
    }

    private LoginUser login0(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // ????????????
        Authentication authentication;
        try {
            // ?????? Spring Security ??? AuthenticationManager#authenticate(...) ???????????????????????????????????????
            // ??????????????????????????? loadUserByUsername ??????????????? User ??????
            authentication = authenticationManager.authenticate(new MultiUsernamePasswordAuthenticationToken(
                    username, password, getUserType()));
        } catch (BadCredentialsException badCredentialsException) {
            this.createLoginLog(username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        } catch (DisabledException disabledException) {
            this.createLoginLog(username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        } catch (AuthenticationException authenticationException) {
            log.error("[login0][username({}) ??????????????????]", username, authenticationException);
            this.createLoginLog(username, logTypeEnum, LoginResultEnum.UNKNOWN_ERROR);
            throw exception(AUTH_LOGIN_FAIL_UNKNOWN);
        }
        Assert.notNull(authentication.getPrincipal(), "Principal ????????????");
        return (LoginUser) authentication.getPrincipal();
    }

    private void createLoginLog(String username, LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // ????????????
        AdminUserDO user = userService.getUserByUsername(username);
        // ??????????????????
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        if (user != null) {
            reqDTO.setUserId(user.getId());
        }
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // ????????????????????????
        if (user != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(user.getId(), ServletUtils.getClientIP());
        }
    }

    /**
     * ?????? User ???????????????????????????
     *
     * @param userId ????????????
     * @return ??????????????????
     */
    private Set<Long> getUserRoleIds(Long userId) {
        return permissionService.getUserRoleIds(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
    }

    @Override
    public String socialLogin(AuthSocialLoginReqVO reqVO, String userIp, String userAgent) {
        // ?????? code ??????????????????????????????????????????????????????????????????
        Long userId = socialUserService.getBindUserId(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (userId == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // ????????????
        AdminUserDO user = userService.getUser(userId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // ?????? LoginUser ??????
        LoginUser loginUser = this.buildLoginUser(user);

        // ??????????????????????????????
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(loginUser.getId(), getUserType().getValue(), reqVO));

        // ????????????????????? Redis ???????????? sessionId ??????
        return createUserSessionAfterLoginSuccess(loginUser, LoginLogTypeEnum.LOGIN_SOCIAL, userIp, userAgent);
    }

    @Override
    public String socialLogin2(AuthSocialLogin2ReqVO reqVO, String userIp, String userAgent) {
        // ?????? code ????????????????????????
        AuthUser authUser = socialUserService.getAuthUser(reqVO.getType(), reqVO.getCode(), reqVO.getState());
        Assert.notNull(authUser, "?????????????????????");

        // ????????????????????????????????????
        LoginUser loginUser = this.login0(reqVO.getUsername(), reqVO.getPassword());

        // ??????????????????????????????
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(loginUser.getId(), getUserType().getValue(), reqVO));

        // ????????????????????? Redis ???????????? sessionId ??????
        return createUserSessionAfterLoginSuccess(loginUser, LoginLogTypeEnum.LOGIN_SOCIAL, userIp, userAgent);
    }

    private String createUserSessionAfterLoginSuccess(LoginUser loginUser, LoginLogTypeEnum logType, String userIp, String userAgent) {
        // ??????????????????
        createLoginLog(loginUser.getUsername(), logType, LoginResultEnum.SUCCESS);
        // ????????????????????? Redis ???????????? sessionId ??????
        return userSessionService.createUserSession(loginUser, userIp, userAgent);
    }

    @Override
    public void socialBind(Long userId, AuthSocialBindReqVO reqVO) {
        // ??????????????????????????????
        socialUserService.bindSocialUser(AuthConvert.INSTANCE.convert(userId, getUserType().getValue(), reqVO));
    }

    @Override
    public void logout(String token) {
        // ??????????????????
        LoginUser loginUser = userSessionService.getLoginUser(token);
        if (loginUser == null) {
            return;
        }
        // ?????? session
        userSessionService.deleteUserSession(token);
        // ??????????????????
        this.createLogoutLog(loginUser.getId(), loginUser.getUsername());
    }

    @Override
    public UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    private void createLogoutLog(Long userId, String username) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_SELF.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    @Override
    public LoginUser verifyTokenAndRefresh(String token) {
        // ?????? LoginUser
        LoginUser loginUser = userSessionService.getLoginUser(token);
        if (loginUser == null) {
            return null;
        }
        // ?????? LoginUser ??????
        return this.refreshLoginUserCache(token, loginUser);
    }

    private LoginUser refreshLoginUserCache(String token, LoginUser loginUser) {
        // ??? 1/3 ??? Session ????????????????????? LoginUser ??????
        if (System.currentTimeMillis() - loginUser.getUpdateTime().getTime() <
                userSessionService.getSessionTimeoutMillis() / 3) {
            return loginUser;
        }

        // ???????????? AdminUserDO ??????
        AdminUserDO user = userService.getUser(loginUser.getId());
        if (user == null || CommonStatusEnum.DISABLE.getStatus().equals(user.getStatus())) {
            throw exception(AUTH_TOKEN_EXPIRED); // ?????? token ????????????????????????????????????????????? token ??????????????????????????????????????????
        }

        // ?????? LoginUser ??????
        LoginUser newLoginUser= this.buildLoginUser(user);
        userSessionService.refreshUserSession(token, newLoginUser);
        return newLoginUser;
    }

    private LoginUser buildLoginUser(AdminUserDO user) {
        LoginUser loginUser = AuthConvert.INSTANCE.convert(user);
        // ????????????
        loginUser.setDeptId(user.getDeptId());
        loginUser.setRoleIds(this.getUserRoleIds(loginUser.getId()));
        return loginUser;
    }

    public void sendSmsCode(Long loginUserId, AuthSendSmsReqVO reqVO) {
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    public String smsLogin(AuthSmsLoginReqVO reqVO, String clientIP, String userAgent) {
        // ???????????????
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_LOGIN.getScene(), clientIP));

        // ????????????????????????
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(AUTH_LOGIN_MOBILE_NOT_FOUND);
        }
        // ????????????
        LoginUser loginUser = AuthConvert.INSTANCE.convert(user);
        loginUser.setRoleIds(this.getUserRoleIds(loginUser.getId()));

        // ????????????????????? Redis ???????????? sessionId ??????
        return createUserSessionAfterLoginSuccess(loginUser, LoginLogTypeEnum.LOGIN_SMS, clientIP, userAgent);
    }

    public void resetPassword(AuthResetPasswordReqVO reqVO) {
        AdminUserDO userDO = checkUserIfExists(reqVO.getMobile());

        // ???????????????
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_FORGET_PASSWORD, getClientIP()));

        // ????????????
        userService.updateUserPassword(userDO.getId(), reqVO.getPassword());
    }

    public AdminUserDO checkUserIfExists(String mobile) {
        AdminUserDO adminUserDO = userService.getUserByMobile(mobile);
        if (adminUserDO == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return adminUserDO;
    }

    public String register(AuthRegisterReqVO reqVO, String clientIP, String userAgent) {
        //
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_REGISTER.getScene(), clientIP));

        // ????????????
        Long user = userService.createUser(AuthConvert.INSTANCE.convert(reqVO));
        AdminUserDO adminUserDO= userService.getUser(user);
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        assignNormalUserRole(user);
        // ????????????
        LoginUser loginUser = AuthConvert.INSTANCE.convert(adminUserDO);

        // ????????????????????? Redis ???????????? sessionId ??????
        return createUserSessionAfterLoginSuccess(loginUser, LoginLogTypeEnum.LOGIN_USERNAME, clientIP, userAgent);
    }

    public void assignNormalUserRole(Long id){
        RoleDO role = roleService.getRoleByCode(RoleCodeEnum.NORMAL_USER.getCode());
        permissionService.assignUserRole(id, Collections.singleton(role.getId()));
    }
}
