package cn.iocoder.lfl.module.system.service.auth;

import cn.hutool.core.lang.UUID;
import cn.iocoder.lfl.framework.common.util.servlet.ServletUtils;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import cn.iocoder.lfl.module.system.service.logger.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.iocoder.lfl.framework.common.exception.enums.ErrorCodeEnum;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.dal.mysql.user.AdminUserMapper;
import cn.iocoder.lfl.module.system.dal.redis.oauth2.LoginUserRedisDAO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;


@Service
public class AdminAuthServiceImpl extends ServiceImpl<AdminUserMapper, AdminUserDo> implements AdminAuthService {

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private LoginLogService loginLogService;

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        AdminUserDo adminUserDo = authenticate(reqVO);
        loginLogService.createLoginLog(LoginLogDO.builder()
                .userId(adminUserDo.getId())
                .username(adminUserDo.getUsername())
                .userIp(ServletUtils.getClientIP())
                .userAgent(ServletUtils.getUserAgent())
                .type(1)
                .result(true)
                .createTime(LocalDateTime.now())
                .build()
        );
        String token = UUID.fastUUID().toString();
        long instants = System.currentTimeMillis()+30*60*1000;
        LoginUser loginUser = LoginUser.builder()
                .id(adminUserDo.getId())
                .username(adminUserDo.getUsername())
                .scopes(List.of("admin"))
                .expiresTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(instants), ZoneId.systemDefault()))
                .loginTime(LocalDateTime.now()).build();

        loginUserRedisDAO.set(token,loginUser);
        return new AuthLoginRespVO(token,loginUser);
    }

    private AdminUserDo authenticate(AuthLoginReqVO reqVO) {
        QueryWrapper<AdminUserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", reqVO.getUsername());
        AdminUserDo bean = this.getOne(queryWrapper);
        if(bean==null){
            throw exception(ErrorCodeEnum.USER_NOT_EXIST);
        }else if(!passwordEncoder.matches(reqVO.getPassword(),bean.getPassword())){
            throw exception(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
        }
        return bean;
    }
}
