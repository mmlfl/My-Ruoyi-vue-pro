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

@TableName("system_oauth2_refresh_token")
@Data
@Accessors(chain = true)
public class OAuth2RefreshTokenDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String refreshToken;

    private Integer userType;

    private String clientId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    private LocalDateTime expiresTime;
}
