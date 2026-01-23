package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 字典数据创建/修改 Request VO")
@Data
public class DictDataSaveReqVO {
    @Schema(description = "字典类型编号", example = "1024")
    private Long id;

    @Schema(description = "字典类型编号",requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String dictType;

    @Schema(description = "标签名",requiredMode = Schema.RequiredMode.REQUIRED, example = "默认")
    private String label;

    @Schema(description = "字典值",requiredMode = Schema.RequiredMode.REQUIRED, example = "default")
    private String value;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "颜色类型,默认默认",example = "default")
    private String colorType;

    @Schema(description = "CSS 样式",example = "default")
    private String cssClass;

    @Schema(description = "备注",example = "默认")
    private String remark;
}
