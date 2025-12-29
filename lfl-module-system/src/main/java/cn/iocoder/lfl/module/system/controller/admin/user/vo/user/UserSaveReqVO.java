package cn.iocoder.lfl.module.system.controller.admin.user.vo.user;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.lfl.framework.common.validation.Mobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Schema(description = "管理后台 - 用户创建 Request VO")
@Data
public class UserSaveReqVO {

    @Schema(description = "用户编号", example = "1024")
    private Long id;

    @Schema(description = "用户名",requiredMode = Schema.RequiredMode.REQUIRED, example = "lfl")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "用户账号由 数字、字母 组成")
    @Size(min = 3,max = 20, message = "用户账号长度为 3-20 个字符")
    private String username;

    @Schema(description = "用户昵称", example = "小王")
    @Size(max = 30, message = "用户昵称长度不能超过 30 个字符")
    private String nickname;

    @Schema(description = "备注", example = "我是一个用户")
    private String remark;

    @Schema(description = "用户性别,参见 SexEnum 枚举类", example = "1")
    private String sex;

    @Schema(description = "部门编号", example = "1")
    private Long deptId;

    @Schema(description = "岗位编号数组", example = "[1]")
    private Set<Long> postIds;

    @Schema(description = "邮箱", example = "lfl@iocoder.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @Schema(description = "头像", example = "https://www.iocoder.cn/xx.png")
    private String avatar;

    @Schema(description = "手机号码", example = "15601691300")
    @Mobile
    private String mobile;

    //=====  以下字段是修改时才会用上的 ========

    @Schema(description = "密码",requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @Size(min = 6,max = 20, message = "密码长度为 6-20 个字符")
    private String password;

    @AssertTrue(message = "密码不能为空")
    @JsonIgnore
    public boolean isPasswordValid() {
        return id != null
                || (ObjectUtil.isAllNotEmpty( password));
    }
}
