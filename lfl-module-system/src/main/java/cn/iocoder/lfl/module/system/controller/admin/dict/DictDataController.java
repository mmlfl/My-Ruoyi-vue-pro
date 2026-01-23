package cn.iocoder.lfl.module.system.controller.admin.dict;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData.DictDataSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.lfl.module.system.service.dict.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {
    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
    @Operation(summary = "创建字典数据")
    public CommonResult<Long> createDictData(@RequestBody @Valid DictDataSaveReqVO reqVO){
        Long id = dictDataService.createDictData(reqVO);
        return CommonResult.success(id);
    }

    @PostMapping("/update")
    @Operation(summary = "更新字典数据")
    public CommonResult<Boolean> updateDictData(@RequestBody @Valid DictDataSaveReqVO reqVO){
        dictDataService.updateDictData(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典数据")
    public CommonResult<Boolean> deleteDictData(@RequestParam("id") Long id){
        dictDataService.deleteDictData(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除字典数据")
    public CommonResult<Boolean> deleteDictDataList(@RequestParam("ids")List<Long> ids){
        dictDataService.deleteList(ids);
        return CommonResult.success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典数据分页")
    public CommonResult<PageResult<DictDataRespVO>> getDictDataPage(@Validated DictDataPageReqVO pageVO){
        PageResult<DictDataDO> dictDataPage = dictDataService.getDictDataPage(pageVO);
        return CommonResult.success(BeanUtils.toBean(dictDataPage,DictDataRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获得字典数据")
    public CommonResult<DictDataRespVO> getDictData(@RequestParam("id") Long id){
        DictDataDO dictData = dictDataService.selectById(id);
        return CommonResult.success(BeanUtils.toBean(dictData,DictDataRespVO.class));
    }

}
