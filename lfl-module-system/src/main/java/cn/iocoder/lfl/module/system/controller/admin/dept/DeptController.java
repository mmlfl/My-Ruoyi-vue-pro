package cn.iocoder.lfl.module.system.controller.admin.dept;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.lfl.module.system.service.dept.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/system/detp")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建部门")
    public CommonResult<Long> createDept(@RequestBody @Valid DeptSaveReqVO reqVO){
        Long id = deptService.createDept(reqVO);
        return CommonResult.success(id);
    }

    @PostMapping("/update")
    @Operation(summary = "更新部门")
    public CommonResult<Boolean> updateDept(@RequestBody @Valid DeptSaveReqVO reqVO){
        deptService.updateDept(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除部门")
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id){
        deptService.deleteDept(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除部门")
    public CommonResult<Boolean> deleteDeptList(@RequestParam("ids") List<Long> ids){
        deptService.deleteDeptList(ids);
        return CommonResult.success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得部门详细信息")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        DeptRespVO dept = deptService.getDept(id);
        return CommonResult.success(dept);
    }

    @GetMapping("list")
    @Operation(summary = "获得部门列表")
    public CommonResult<List<DeptRespVO>> getDeptList(DeptListReqVO reqVO) {
        List<DeptDO> deptList = deptService.getDeptList(reqVO);
        return CommonResult.success(BeanUtils.toBean(deptList,DeptRespVO.class));
    }

    @GetMapping(value = {"list-all-simple","simple-list"})
    @Operation(summary = "获得部门精简列表", description = "只包含被开启的部门，主要用于前端的下拉选项")
    public CommonResult<List<DeptSimpleRespVO>> getSimpleDeptList(){
        List<DeptDO> deptList = deptService.getDeptList(
                new DeptListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus())
        );
        return CommonResult.success(BeanUtils.toBean(deptList,DeptSimpleRespVO.class));
    }
}
