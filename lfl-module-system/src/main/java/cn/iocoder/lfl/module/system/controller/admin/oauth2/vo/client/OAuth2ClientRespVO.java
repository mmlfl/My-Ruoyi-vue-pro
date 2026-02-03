package cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client;

import java.time.LocalDateTime;

public class OAuth2ClientRespVO {
    private String clientId;

    private String secret;

    private String name;

    private String logo;

    private Integer status;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private String authorizedGrantTypes;

    private LocalDateTime createTime;
}
