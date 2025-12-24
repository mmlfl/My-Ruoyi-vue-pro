package cn.iocoder.lfl.module.system.dal.dataobject.user;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@TableName("system_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserDO extends BaseDO {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String remark;

    private Long deptId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<Long> postIds;

    private String email;

    private String mobile;

    private Integer sex;

    private String avatar;

    private Integer status;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;


}
