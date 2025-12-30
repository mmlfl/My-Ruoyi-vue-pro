package cn.iocoder.lfl.module.system.dal.dataobject.dept;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("system_post")
@Data
public class PostDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    private Integer sort;

    private Integer status;

    private String remark;
}
