package cn.iocoder.lfl.module.system.controller.admin.dict;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypePageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSimpleRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.lfl.module.system.service.dict.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {
    @Resource
    private DictTypeService dictTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
    public CommonResult<Long> createDictType(@RequestBody @Valid DictTypeSaveReqVO reqVO){
        Long id = dictTypeService.createDictType(reqVO);
        return CommonResult.success(id);
    }

    @PostMapping("/update")
    @Operation(summary = "修改字典类型")
    public CommonResult<Boolean> updateDictType(@RequestBody @Valid DictTypeSaveReqVO reqVO){
        dictTypeService.updateDictType(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典类型")
    public CommonResult<Boolean> deleteDictType(@RequestParam("id") Long id){
        dictTypeService.deleteDictType(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除字典类型")
    public CommonResult<Boolean> deleteDictTypeList(@RequestParam("ids") List<Long> ids){
        dictTypeService.deleteDictTypeList(ids);
        return CommonResult.success(true);
    }

    @GetMapping(value = {"/list-all-simple","simple-list"})
    @Operation(summary = "获得全部字典类型列表", description = "包含开启 + 禁用的字典类型,主要用于前端的下拉选项")
    public CommonResult<List<DictTypeSimpleRespVO>> getSimpleDictTypeList(){
        List<DictTypeSimpleRespVO> dictTypeList = dictTypeService.getSimpleDictTypeList();
        return CommonResult.success(dictTypeList);
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典类型分页")
    public CommonResult<PageResult<DictTypeRespVO>> getDictTypePage(@RequestBody @Valid DictTypePageReqVO pageVO){
        PageResult<DictTypeDO> dictTypePage = dictTypeService.getDictTypePage(pageVO);
        return CommonResult.success(BeanUtils.toBean(dictTypePage,DictTypeRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获得字典类型")
    public CommonResult<DictTypeRespVO> getDictType(@RequestParam("id") Long id){
        return CommonResult.success(dictTypeService.getDictType(id));
    }
}
