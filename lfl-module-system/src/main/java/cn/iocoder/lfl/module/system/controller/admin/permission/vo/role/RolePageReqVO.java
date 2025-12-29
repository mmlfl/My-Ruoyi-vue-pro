package cn.iocoder.lfl.module.system.controller.admin.permission.vo.role;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.lfl.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

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

    @Schema(description = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
