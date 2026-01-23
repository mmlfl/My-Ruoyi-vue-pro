package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 字典类型创建/修改 Request VO")
@Data
public class DictTypeSaveReqVO {
    @Schema(description = "字典编号", example = "1")
    private Long id;

    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户性别")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 20, message = "字典名称长度不能超过20个字符")
    private String name;

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "system_user_sex")
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 30, message = "字典类型长度不能超过30个字符")
    private String type;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "删除时间")
    private LocalDateTime deleteTime;
}
