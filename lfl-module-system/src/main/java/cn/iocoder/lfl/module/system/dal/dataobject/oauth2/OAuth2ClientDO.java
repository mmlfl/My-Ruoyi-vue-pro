package cn.iocoder.lfl.module.system.dal.dataobject.oauth2;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

@TableName("system_oauth2_client")
@Data
public class OAuth2ClientDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String clientId;

    private String secret;

    private String name;

    private String logo;

    private String description;

    private Integer status;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> redirectUris;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorizedGrantTypes;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> autoApproveScopes;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> authorities;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> resourceIds;

    private String additionalInformation;
}
