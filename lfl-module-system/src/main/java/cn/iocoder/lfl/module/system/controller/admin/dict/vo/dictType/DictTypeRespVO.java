package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 字典类型返回信息 Response VO")
@Data
public class DictTypeRespVO {
    @Schema(description = "字典编号",requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "字典名称",requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    private String name;

    @Schema(description = "字典类型",requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    private String type;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "这是字典的备注")
    private String remark;

    @Schema(description = "创建时间",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}
