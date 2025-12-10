package cn.iocoder.lfl.module.system.controller;

import cn.iocoder.lfl.module.system.pojo.DTO.LoginDTO;
import cn.iocoder.lfl.module.system.pojo.VO.LoginVO;
import cn.iocoder.lfl.module.system.service.UserService;
import org.springframework.web.bind.annotation.*;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.lfl.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO){
        return success(userService.login(loginDTO));
    }

    @GetMapping("test")
    public String test(){
        return "你成功访问了 Test 类!";
    }
}
