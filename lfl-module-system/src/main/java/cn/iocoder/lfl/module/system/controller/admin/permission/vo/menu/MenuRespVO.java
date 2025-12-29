package cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "后台管理 - 菜单 Response VO")
@Data
public class MenuRespVO {
    @Schema(description = "菜单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "父菜单编号",requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Long parentId;

    @Schema(description = "菜单名", requiredMode = Schema.RequiredMode.REQUIRED, example = "菜单")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    @Schema(description = "菜单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "菜单类型不能为空")
    private String type;

    @Schema(description = "菜单图标", example = "菜单图标")
    private String icon;

    @Schema(description = "菜单路由地址", example = "菜单路由地址")
    @Size(max = 200, message = "菜单路由长度不能超过200个字符")
    private String path;

    @Schema(description = "组件路径", example = "组件路径")
    @Size(max = 200, message = "组件路径长度不能超过200个字符")
    private String component;

    @Schema(description = "组件名称", example = "组件名称")
    private String componentName;

    @Schema(description = "权限标识", example = "权限标识")
    @Size(max = 100)
    private String permission;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "可见性",example = "false")
    private Boolean visible;

    @Schema(description = "是否一直显示",example = "false")
    private Boolean alwaysShow;

    @Schema(description = "是否缓存",example = "false")
    private Boolean keepAlive;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED,example = "2021-01-01 00:00:00")
    private LocalDateTime createTime;
}
