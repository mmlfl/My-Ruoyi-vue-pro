package cn.iocoder.lfl.module.system.pojo.DO.permission;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

@TableName("system_role")
@Data
public class Role extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    private Integer sort;

    private Integer dataScope;

    private Set<Long> dataScopeDeptIds;

    private Integer status;

    private Integer type;

    private String remark;

    private Long tenantId;
}
