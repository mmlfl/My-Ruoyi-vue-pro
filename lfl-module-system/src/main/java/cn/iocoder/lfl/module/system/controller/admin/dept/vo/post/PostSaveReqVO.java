package cn.iocoder.lfl.module.system.controller.admin.dept.vo.post;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "管理后台 - 岗位保存 Request VO")
@Data
public class PostSaveReqVO {
    @Schema(description = "岗位编号", example = "1024")
    private Long id;

    @Schema(description = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "普通员工")
    @NotBlank(message = "岗位名称不能为空")
    @Size(max = 30, message = "岗位名称长度不能超过30个字符")
    private String name;

    @Schema(description = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "user")
    @NotBlank(message = "岗位编码不能为空")
    @Size(max = 20, message = "岗位编码长度不能超过20个字符")
    private String code;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    @InEnum(value = CommonStatusEnum.class,message = "必须在指定范围{value}")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
