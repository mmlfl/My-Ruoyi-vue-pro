package com.lfl.yudao.server.controller;

import com.lfl.yudao.server.pojo.CommonResult;
import com.lfl.yudao.server.pojo.DO.User;
import com.lfl.yudao.server.pojo.DTO.LoginDTO;
import com.lfl.yudao.server.pojo.VO.LoginVO;
import com.lfl.yudao.server.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.lfl.yudao.server.pojo.CommonResult.success;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @PermitAll
    public CommonResult<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO){
        return success(userService.login(loginDTO));
    }
}
