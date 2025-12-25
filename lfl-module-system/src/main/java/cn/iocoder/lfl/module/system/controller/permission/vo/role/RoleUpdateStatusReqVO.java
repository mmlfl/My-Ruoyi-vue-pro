package cn.iocoder.lfl.module.system.controller.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 角色更新状态 Request VO")
@Data
public class RoleUpdateStatusReqVO {
    @NotNull(message = "角色编号不能为空")
    @Schema(description = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @NotNull(message = "状态值不能为空")
    @Schema(description = "状态值", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;
}
