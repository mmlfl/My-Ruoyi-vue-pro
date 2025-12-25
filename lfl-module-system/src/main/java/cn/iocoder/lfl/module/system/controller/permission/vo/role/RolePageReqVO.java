package cn.iocoder.lfl.module.system.controller.permission.vo.role;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 角色分页 Request VO")
@Data
public class RolePageReqVO extends PageParam {
    @Schema(description = "角色名称 - 模糊查询", example = "芋道")
    private String name;
    @Schema(description = "角色标识 - 模糊查询", example = "yudao")
    private String code;
    @Schema(description = "角色状态", example = "1")
    private Integer status;
    @Schema(description = "角色类型", example = "1")
    private Integer type;
}
