package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 字典类型精简信息 Response VO")
@Data
public class DictTypeSimpleRespVO {
    @Schema(description = "字典编号",requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "字典名称",requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    private String name;

    @Schema(description = "字典类型",requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;
}
