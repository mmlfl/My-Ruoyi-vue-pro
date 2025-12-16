package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.MenuMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
    @Resource
    private MenuMapper menuMapper;

    @Override
    public MenuDO getMenuIdByPermission(String permission) {
        return null;
    }

    @Override
    @Cacheable(value = RedisConstants.PERMISSION_MENU_ID_LIST,key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuDO> menuDOS = menuMapper.selectListByPermission(permission);
        return CollectionUtils.convertList(menuDOS,MenuDO::getId);
    }


    /**
     * 获取自身的bean对象 使得spring aop可以发挥作用
     */
    private MenuServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
