package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MenuMapper extends BaseMapperX<MenuDO> {

    default List<MenuDO> selectListByPermission(String permission){
        return selectList(MenuDO::getPermission, permission);
    }
}
