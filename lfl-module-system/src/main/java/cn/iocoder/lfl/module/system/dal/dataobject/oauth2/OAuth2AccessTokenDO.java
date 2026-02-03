package cn.iocoder.lfl.module.system.dal.dataobject.oauth2;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@TableName("system_oauth2_access_token")
@Data
@Accessors(chain = true)
public class OAuth2AccessTokenDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Integer userType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String,String> userInfo;

    private String accessToken;

    private String refreshToken;

    private String clientId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    private LocalDateTime expiresTime;
}
