package cn.iocoder.lfl.module.system.dal.dataobject.permission;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.Set;

@TableName("system_role")
@Data
public class RoleDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    private Integer sort;

    private Integer dataScope;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<Long> dataScopeDeptIds;

    private Integer status;

    private Integer type;

    private String remark;

    private Long tenantId;
}
