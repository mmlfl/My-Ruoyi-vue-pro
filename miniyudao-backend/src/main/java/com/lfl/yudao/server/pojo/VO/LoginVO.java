package com.lfl.yudao.server.pojo.VO;

import com.lfl.lfl.framework.security.LoginUser;
import com.lfl.yudao.server.pojo.DO.User;
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
