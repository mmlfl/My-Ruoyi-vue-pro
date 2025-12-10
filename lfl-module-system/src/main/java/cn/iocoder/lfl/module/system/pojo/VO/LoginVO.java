package cn.iocoder.lfl.module.system.pojo.VO;

import cn.iocoder.lfl.framework.security.core.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String token;
    private LoginUser user;
}
