package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface RoleMenuMapper extends BaseMapperX<RoleMenuDO> {
    default List<RoleMenuDO> selectListByMenuId(Long menuId){
        return selectList(RoleMenuDO::getMenuId, menuId);
    }
}
