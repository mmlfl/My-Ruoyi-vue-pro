package cn.iocoder.lfl.module.system.controller.admin.dict;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType.DictTypeSaveReqVO;
import cn.iocoder.lfl.module.system.service.dict.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    }
}
