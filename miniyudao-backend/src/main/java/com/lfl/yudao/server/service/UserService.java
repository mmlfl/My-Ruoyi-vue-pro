package com.lfl.yudao.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfl.yudao.server.pojo.DO.User;
import com.lfl.yudao.server.pojo.DTO.LoginDTO;
import com.lfl.yudao.server.pojo.VO.LoginVO;

public interface UserService extends IService<User> {
    LoginVO login(LoginDTO loginDTO);
}
