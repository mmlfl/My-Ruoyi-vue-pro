package cn.iocoder.lfl.module.system.controller.dept.vo;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.validation.InEnum;
import cn.iocoder.lfl.framework.common.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "管理后台 - 部门创建/修改 Request VO")
@Data
public class DeptSaveReqVO {
    @Schema(description = "部门编号", example = "1024")
    private Long id;

    @Schema(description = "父部门编号", example = "1024")
    private Long parentId;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "研发部")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    private String name;

    @Schema(description = "显示顺序",requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "负责人",example = "1")
    private Long leaderUserId;

    @Schema(description = "部门手机",example = "15601691300")
    @Mobile
    private String phone;

    @Schema(description = "部门邮箱",example = "15601691300@qq.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "部门状态",requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "部门状态不能为空")
    @InEnum(value = CommonStatusEnum.class,message = "部门状态必须是 {value}")
    private Integer status;
}
