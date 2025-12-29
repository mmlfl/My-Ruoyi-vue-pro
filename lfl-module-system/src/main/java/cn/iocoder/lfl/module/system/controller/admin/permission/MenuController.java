package cn.iocoder.lfl.module.system.controller.admin.permission;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuSaveReqVO;
import cn.iocoder.lfl.module.system.service.permission.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "后台管理-菜单")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    public CommonResult<Long> createMenu(@RequestBody @Valid MenuSaveReqVO reqVO){
        Long id = menuService.createMenu(reqVO);
        return CommonResult.success(id);
    }

    @PostMapping("/update")
    @Operation(summary = "更新菜单")
    public CommonResult<Boolean> updateMenu(@RequestBody @Valid MenuSaveReqVO reqVO){
        menuService.updateMenu(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id){
        menuService.deleteMenu(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除菜单")
    public CommonResult<Boolean> deleteMenuList(@RequestParam("ids") List<Long> ids){
        menuService.deleteMenuList(ids);
        return CommonResult.success(true);
    }

    @GetMapping("get")
    @Operation(summary = "获取菜单信息")
    public CommonResult<MenuRespVO> getMenu(@RequestParam("id") Long id){
        MenuRespVO respVO = menuService.getMenu(id);
        return CommonResult.success(respVO);
    }

    @GetMapping("list")
    @Operation(summary = "获取菜单列表",description = "用于【菜单管理】的列表展示")
    public CommonResult<List<MenuRespVO>> getMenuList(MenuListReqVO reqVO){
        List<MenuRespVO> list = menuService.getMenuList(reqVO);
        return CommonResult.success(list);
    }
}
