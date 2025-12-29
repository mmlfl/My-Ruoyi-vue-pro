package cn.iocoder.lfl.module.system.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RoleRespVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RoleUpdateStatusReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.lfl.module.system.service.permission.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台-角色")
@RestController
@RequestMapping("system/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/create")
    @Operation(summary = "创建角色")
    public CommonResult<Long> createRole(@RequestBody @Valid RoleSaveReqVO reqVO){
        return CommonResult.success(roleService.createRole(reqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除角色")
    public CommonResult<Boolean> deleteRole(@RequestParam("id")Long id){
        roleService.deleteRole(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除角色")
    public CommonResult<Boolean> deleteRoleList(@RequestParam("ids") List<Long> ids){
        roleService.deleteRoleList(ids);
        return CommonResult.success(true);
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    public CommonResult<Boolean> updateRole(@RequestBody @Valid RoleSaveReqVO reqVO){
        roleService.updateRole(reqVO);
        return CommonResult.success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改角色状态")
    public CommonResult<Boolean> updateRoleStatus(@RequestBody @Valid RoleUpdateStatusReqVO reqVO){
        roleService.updateRoleStatus(reqVO.getId(),reqVO.getStatus());
        return CommonResult.success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得角色")
    public CommonResult<RoleRespVO> getRole(@RequestParam("id")Long id){
        RoleRespVO respVO = roleService.getRole(id);
        return CommonResult.success(respVO);
    }

    @PostMapping("/page")
    @Operation(summary = "获得角色分页")
    public CommonResult<PageResult<RoleRespVO>> getRolePage(@RequestBody @Valid RolePageReqVO reqVO){
        PageResult<RoleDO> pageResult = roleService.getRolePage(reqVO);
        //1.校验列表是否为空
        if(CollUtil.isEmpty(pageResult.getList())){
            return CommonResult.success(PageResult.empty());
        }
        return CommonResult.success(BeanUtils.toBean(pageResult,RoleRespVO.class));
    }
}
