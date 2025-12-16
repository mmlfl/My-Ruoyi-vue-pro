package cn.iocoder.lfl.module.system.controller.admin.auth;

import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.service.auth.AdminAuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.lfl.framework.common.pojo.CommonResult.success;

@RestController
@RequestMapping("system/auth")
public class AuthController {

    @Resource
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO){
        return success(adminAuthService.login(reqVO));
    }

    @PreAuthorize("@ss.hasPermission('system_auth_test')")
    @GetMapping("test")
    public String test(){
        return "你成功访问了 Test 类!";
    }
}
