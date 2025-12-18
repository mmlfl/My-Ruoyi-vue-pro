package cn.iocoder.lfl.module.system.controller.user;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.module.system.controller.user.vo.user.UserSaveReqVO;
import cn.iocoder.lfl.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "管理后台-用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {
    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/create")
    public CommonResult<Long> createUser(@RequestBody @Valid UserSaveReqVO reqVO){
        Long id = adminUserService.createUser(reqVO);
        return CommonResult.success(id);
    }
}
