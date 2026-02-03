package cn.iocoder.lfl.module.system.service.auth;

import cn.hutool.core.lang.UUID;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.enums.UserTypeEnum;
import cn.iocoder.lfl.framework.common.util.servlet.ServletUtils;
import cn.iocoder.lfl.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.enums.logger.LoginLogTypeEnum;
import cn.iocoder.lfl.module.system.enums.logger.LoginResultEnum;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import cn.iocoder.lfl.module.system.service.logger.LoginLogService;
import cn.iocoder.lfl.module.system.service.user.AdminUserService;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.lfl.module.system.dal.redis.oauth2.LoginUserRedisDAO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;
import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;


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
    private AdminUserMapper userMapper;

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        AdminUserDO user = authenticate(reqVO);
        String token = UUID.fastUUID().toString();
        long instants = System.currentTimeMillis()+30*60*1000;
        LoginUser loginUser = LoginUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .scopes(List.of("admin"))
                .expiresTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(instants), ZoneId.systemDefault()))
                .loginTime(LocalDateTime.now()).build();

        loginUserRedisDAO.set(token,loginUser);
        createLoginLog(user.getId(),user.getUsername(),
                LoginLogTypeEnum.LOGIN_USERNAME,LoginResultEnum.SUCCESS);
        return new AuthLoginRespVO(token,loginUser);
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

    private AdminUserDO authenticate(AuthLoginReqVO reqVO) {
        AdminUserDO user = userMapper.selectOne(AdminUserDO::getUsername, reqVO.getUsername());

        if(user==null){
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }else if(!passwordEncoder.matches(reqVO.getPassword(),user.getPassword())){
            throw exception(ErrorCodeConstants.USER_PASSWORD_FAILED);
        }
        return user;
    }
}
