package cn.iocoder.lfl.module.system.dal.dataobject.permission;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("system_user_role")
@Data
public class UserRoleDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;
}
