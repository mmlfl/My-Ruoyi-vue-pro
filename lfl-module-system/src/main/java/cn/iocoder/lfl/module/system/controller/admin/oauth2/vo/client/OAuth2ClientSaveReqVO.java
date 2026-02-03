package cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OAuth2ClientSaveReqVO {
    private Long id;

    @NotEmpty(message = "客户端编号不能为空")
    private String clientId;

    @NotEmpty(message = "客户端密钥不能为空")
    private String secret;

    @NotEmpty(message = "客户端名称不能为空")
    private String name;

    @NotNull(message = "应用图标不能为空")
    @URL(message = "应用图标的地址不明确")
    private String logo;

    private String description;

    @NotNull(message = "状态不能为空")
    private Integer status;

    @NotNull(message = "访问令牌有效期不能为空")
    @Min(value = 1, message = "访问令牌有效期不能小于 1")
    private Integer accessTokenValiditySeconds;

    @NotNull(message = "刷新令牌有效期不能为空")
    @Min(value = 1, message = "刷新令牌有效期不能小于 1")
    private Integer refreshTokenValiditySeconds;

    @Schema(description = "可重定向的 URI 地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "可重定向的 URI 地址不能为空")
    private List<@NotEmpty(message = "重定向的 URI 不能为空") @URL(message = "重定向的 URI 格式不正确") String> redirectUris;

    @Schema(description = "授权类型，参见 OAuth2GrantTypeEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "password")
    @NotNull(message = "授权类型不能为空")
    private List<String> authorizedGrantTypes;

    @Schema(description = "授权范围", example = "user_info")
    private List<String> scopes;

    @Schema(description = "自动通过的授权范围", example = "user_info")
    private List<String> autoApproveScopes;

    @Schema(description = "权限", example = "system:user:query")
    private List<String> authorities;

    @Schema(description = "资源", example = "1024")
    private List<String> resourceIds;

    private String additionalInformation;
}
