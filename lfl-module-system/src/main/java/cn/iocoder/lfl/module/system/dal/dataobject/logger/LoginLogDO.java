package cn.iocoder.lfl.module.system.dal.dataobject.logger;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@TableName("system_login_log")
@Data
@Builder
public class LoginLogDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("type")
    private Integer type;
    @TableField("result")
    private Boolean result;
    @TableField("user_ip")
    private String userIp;
    @TableField("user_agent")
    private String userAgent;
    @TableField("create_time")
    private LocalDateTime createTime;
}
