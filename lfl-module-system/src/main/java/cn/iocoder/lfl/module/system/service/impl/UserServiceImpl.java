package cn.iocoder.lfl.module.system.service.impl;

import cn.hutool.core.lang.UUID;
import cn.iocoder.lfl.framework.common.util.servlet.ServletUtils;
import cn.iocoder.lfl.module.system.pojo.DO.SysLoginLog;
import cn.iocoder.lfl.module.system.service.LoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.iocoder.lfl.framework.common.exception.enums.ErrorCodeEnum;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.mapper.UserMapper;
import cn.iocoder.lfl.module.system.pojo.DAO.LoginUserRedisDAO;
import cn.iocoder.lfl.module.system.pojo.DO.User;
import cn.iocoder.lfl.module.system.pojo.DTO.LoginDTO;
import cn.iocoder.lfl.module.system.pojo.VO.LoginVO;
import cn.iocoder.lfl.module.system.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private LoginLogService loginLogService;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = authenticate(loginDTO);
        loginLogService.createLoginLog(SysLoginLog.builder()
                .userId(user.getId())
                .username(user.getUsername())
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
                .id(user.getId())
                .username(user.getUsername())
                .scopes(List.of("admin"))
                .expiresTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(instants), ZoneId.systemDefault()))
                .loginTime(LocalDateTime.now()).build();

        loginUserRedisDAO.set(token,loginUser);
        return new LoginVO(token,loginUser);
    }

    private User authenticate(LoginDTO loginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginDTO.getUsername());
        User bean = this.getOne(queryWrapper);
        if(bean==null){
            throw exception(ErrorCodeEnum.USER_NOT_EXIST);
        }else if(!passwordEncoder.matches(loginDTO.getPassword(),bean.getPassword())){
            throw exception(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
        }
        return bean;
    }
}
