package cn.iocoder.lfl.module.system.controller.user;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.user.vo.user.*;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.lfl.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台-用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {
    @Resource
    private AdminUserService userService;

    @PostMapping("/create")
    @Operation(summary = "创建用户")
    public CommonResult<Long> createUser(@RequestBody @Valid UserSaveReqVO reqVO){
        Long id = userService.createUser(reqVO);
        return CommonResult.success(id);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id){
        userService.deleteUser(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除用户")
    public CommonResult<Boolean> deleteUserList(@RequestParam("ids") List<Long> ids){
        userService.deleteUserList(ids);
        return CommonResult.success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户")
    public CommonResult<Boolean> updateUser(@RequestBody @Valid UserSaveReqVO reqVO){
        userService.updateUser(reqVO);
        return CommonResult.success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户密码")
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid UserUpdatePasswordReqVO reqVO){
        userService.updateUserPassword(reqVO.getId(),reqVO.getPassword());
        return CommonResult.success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改用户状态")
    public CommonResult<Boolean> updateUserStatus(@RequestBody @Valid UserUpdateStatusReqVO reqVO){
        userService.updateUserStatus(reqVO.getId(),reqVO.getStatus());
        return CommonResult.success(true);
    }

    @PostMapping("/page")
    @Operation(summary = "获得用户分页列表")
    public CommonResult<PageResult<UserRespVO>> getUserPage(@RequestBody @Validated UserPageReqVO reqVO){
        PageResult<AdminUserDO> page = userService.getUserPage(reqVO);
        if(page.getList().isEmpty()){
            return CommonResult.success(PageResult.empty());
        }
        List<UserRespVO> list = page.getList().stream().map(user -> {
            return BeanUtils.toBean(user, UserRespVO.class);
        }).toList();

        return CommonResult.success(new PageResult<>(list, page.getTotal()));
    }


    @GetMapping("/get")
    @Operation(summary = "获得用户详情")
    @Parameter(name = "id", description = "用户编号", required = true, example = "1024")
    public CommonResult<UserRespVO> getUser(@RequestParam("id") Integer id){
        UserRespVO user = userService.getUser(id);
        return CommonResult.success(user);
    }


}
