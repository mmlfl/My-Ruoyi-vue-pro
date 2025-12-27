package cn.iocoder.lfl.module.system.dal.dataobject.permission;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("system_menu")
@Data
public class MenuDO extends BaseDO {

    /**
     * 菜单编号 - 根节点
     */
    public static final Long ID_ROOT = 0L;


    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String permission;

    private Integer type;

    private Integer sort;

    private Long parentId;

    private String path;

    private String icon;

    private String component;

    private String componentName;

    private Integer status;

    private Boolean visible;

    private Boolean keepAlive;

    private Boolean alwaysShow;
}
