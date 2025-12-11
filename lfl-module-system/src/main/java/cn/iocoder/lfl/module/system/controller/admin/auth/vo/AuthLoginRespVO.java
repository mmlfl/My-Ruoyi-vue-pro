package cn.iocoder.lfl.module.system.controller.admin.auth.vo;

import cn.iocoder.lfl.framework.security.core.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthLoginRespVO {
    private String accessToken;

    private LoginUser loginUser;
}
