package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.permission.vo.menu.MenuListReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapperX<MenuDO> {

    default List<MenuDO> selectListByPermission(String permission){
        return selectList(MenuDO::getPermission, permission);
    }

    default MenuDO selectByParentIdAndName(Long parentId, String name){
        return selectOne(MenuDO::getParentId,parentId,MenuDO::getName,name);
    }

    default MenuDO selectByComponentName(String componentName){
        return selectOne(MenuDO::getComponentName,componentName);
    }

    default List<MenuDO> selectList(MenuListReqVO reqVO){
        return selectList(new LambdaQueryWrapperX<MenuDO>()
                .likeIfPresent(MenuDO::getName, reqVO.getName())
                .eqIfPresent(MenuDO::getStatus, reqVO.getStatus())
        );
    }
}
