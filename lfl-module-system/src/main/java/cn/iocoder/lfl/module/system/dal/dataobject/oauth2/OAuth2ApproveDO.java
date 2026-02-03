package cn.iocoder.lfl.module.system.dal.dataobject.oauth2;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@TableName("system_oauth2_approve")
@Data
@Accessors(chain = true)
public class OAuth2ApproveDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Integer userType;

    private String clientId;

    private String scope;

    private Boolean approved;

    private LocalDateTime expiresTime;
}
