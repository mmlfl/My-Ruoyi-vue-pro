package cn.iocoder.lfl.module.system.dal.dataobject.dept;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("system_dept")
@Data
public class DeptDO extends BaseDO {

    /**
     * 顶级部门编号
     */
    public static final Long PARENT_ROOT_ID = 0L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;

    private String name;

    private Integer sort;

    private Long leaderUserId;

    private String phone;

    private String email;

    private Integer status;
}
