package cn.iocoder.lfl.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "管理后台 - 角色 Response VO")
@Data
public class RoleRespVO {
    @Schema(description = "角色编号",requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "角色名称",requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    private String name;

    @Schema(description = "角色标识",requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    private String code;

    @Schema(description = "角色排序",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;

    @Schema(description = "角色状态",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "角色类型",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "备注",example = "我是一个普通角色")
    private String remark;

    @Schema(description = "数据范围",example = "1")
    private Integer dataScope;

    @Schema(description = "数据范围(指定部门数组)", example = "[1]")
    private Set<Long> dataScopeDeptIds;

    @Schema(description = "创建时间",requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}
