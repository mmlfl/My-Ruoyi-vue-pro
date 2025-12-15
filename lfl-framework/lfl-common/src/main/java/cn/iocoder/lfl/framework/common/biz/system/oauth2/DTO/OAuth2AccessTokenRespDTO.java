package cn.iocoder.lfl.framework.common.biz.system.oauth2.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OAuth2AccessTokenRespDTO {

    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
