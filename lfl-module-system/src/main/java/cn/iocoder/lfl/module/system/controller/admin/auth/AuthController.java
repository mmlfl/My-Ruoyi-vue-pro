package cn.iocoder.lfl.module.system.controller.admin.auth;

import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.service.UserService;
import org.springframework.web.bind.annotation.*;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.lfl.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("system/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO){
        return success(userService.login(reqVO));
    }

    @GetMapping("test")
    public String test(){
        return "你成功访问了 Test 类!";
    }
}
