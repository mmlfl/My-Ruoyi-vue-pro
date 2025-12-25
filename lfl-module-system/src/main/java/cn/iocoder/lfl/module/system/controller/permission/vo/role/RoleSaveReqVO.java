package cn.iocoder.lfl.module.system.controller.permission.vo.role;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Schema(description = "管理后台 - 角色创建 Request VO")
@Data
public class RoleSaveReqVO {
    @Schema(description = "角色编号", example = "1024")
    private Long id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String name;

    @Schema(description = "角色编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    @NotBlank(message = "角色编码不能为空")
    @Size(max = 100, message = "角色编码长度不能超过100个字符")
    private String code;

    @Schema(description = "角色排序", example = "1")
    @NotNull(message = "角色排序不能为空")
    private Integer sort;

    @Schema(description = "角色状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "角色状态不能为空")
    @InEnum(value = CommonStatusEnum.class,message = "状态必须是 {value}")
    private Integer status;

    @Schema(description = "角色类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "角色类型不能为空")
    private Integer type;

    @Schema(description = "备注", example = "我是一个普通角色")
    private String remark;
}
