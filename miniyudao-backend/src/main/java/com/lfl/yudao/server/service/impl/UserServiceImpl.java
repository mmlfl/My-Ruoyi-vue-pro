package com.lfl.yudao.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfl.yudao.server.exception.ErrorCodeEnum;
import com.lfl.yudao.server.mapper.UserMapper;
import com.lfl.yudao.server.pojo.DAO.LoginUserRedisDAO;
import com.lfl.yudao.server.pojo.DO.User;
import com.lfl.yudao.server.pojo.DTO.LoginDTO;
import com.lfl.yudao.server.pojo.LoginUser;
import com.lfl.yudao.server.pojo.VO.LoginVO;
import com.lfl.yudao.server.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.lfl.yudao.server.exception.util.ServiceExceptionUtil.exception;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = authenticate(loginDTO);
        String token = UUID.fastUUID().toString();

        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setScopes(List.of("admin"));
        long instants = System.currentTimeMillis()+30*60*1000;
        loginUser.setExpiresTime(Instant.ofEpochMilli(instants).atZone(ZoneId.systemDefault()).toLocalDateTime());
        loginUser.setLoginTime(LocalDateTime.now());
        loginUserRedisDAO.set(token,loginUser);
        return new LoginVO(token,user);
    }

    private User authenticate(LoginDTO loginDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginDTO.getUsername());
        User bean = this.getOne(queryWrapper);
        if(bean==null){
            throw exception(ErrorCodeEnum.USER_NOT_EXIST);
        }else if(!bean.getPassword().equals(loginDTO.getPassword())){
            throw exception(ErrorCodeEnum.USERNAME_PASSWORD_ERROR);
        }
        return bean;
    }
}
