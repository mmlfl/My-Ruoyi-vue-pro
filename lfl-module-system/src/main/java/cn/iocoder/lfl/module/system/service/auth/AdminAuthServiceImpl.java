package cn.iocoder.lfl.module.system.service.auth;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.enums.UserTypeEnum;
import cn.iocoder.lfl.framework.common.util.servlet.ServletUtils;
import cn.iocoder.lfl.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.convert.auth.AuthConvert;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.lfl.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.lfl.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.lfl.module.system.service.logger.LoginLogService;
import cn.iocoder.lfl.module.system.service.oauth2.OAuth2TokenService;
import cn.iocoder.lfl.module.system.service.user.AdminUserService;
import cn.iocoder.lfl.module.system.dal.redis.oauth2.LoginUserRedisDAO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.iocoder.lfl.module.system.enums.oauth2.OAuth2ClientConstants.CLIENT_ID_DEFAULT;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;
import static cn.iocoder.lfl.framework.common.exception.util.ServiceExceptionUtil.exception;


@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private LoginLogService loginLogService;
    @Resource
    private AdminUserService userService;
    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());
        //创建token令牌,记录登录日志
        return createAccessTokenAfterLoginSuccess(user.getId(),reqVO.getUsername(),LoginLogTypeEnum.LOGIN_USERNAME);
    }

    private AuthLoginRespVO createAccessTokenAfterLoginSuccess(Long userId,String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        // 这里更新了 最后登录时间和登录ip
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        //创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(), CLIENT_ID_DEFAULT, null);
        //构建返回结果
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logType, LoginResultEnum result) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType.getType());
        reqDTO.setUserId(userId);
        reqDTO.setUsername(username);
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setResult(result.getResult());
        reqDTO.setUserType(getUserType().getValue());
        loginLogService.createLoginLog(reqDTO);

        if(userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(),result.getResult())){
            userService.updateUserLogin(userId,ServletUtils.getClientIP());
        }
    }

    private UserTypeEnum getUserType(){
        return UserTypeEnum.ADMIN;
    }
}
