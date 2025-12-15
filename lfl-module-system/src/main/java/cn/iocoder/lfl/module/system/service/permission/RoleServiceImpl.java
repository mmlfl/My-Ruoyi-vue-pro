package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.RoleMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisConstants;
import cn.iocoder.lfl.module.system.enums.permission.RoleCodeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RoleServiceImpl extends ServiceImpl<RoleMapper,RoleDO> implements RoleService{

    @Resource
    private RoleMapper roleMapper;

    /**
     * TODO
     * @param roleIds
     * @return
     */
    @Override
    public List<RoleDO> getRoleList(Set<Long> roleIds) {
        return null;
    }

    @Override
    public List<RoleDO> getRoleListFromCache(Collection<Long> roleIds) {
        if(CollUtil.isEmpty(roleIds)){
            return null;
        }
        RoleServiceImpl self = getSelf();
        return CollectionUtils.convertList(roleIds,self::getRoleFromCache);
    }

    @Override
    @Cacheable(value = RedisConstants.ROLE,key = "#roleId",unless = "#result == null")
    public RoleDO getRoleFromCache(Long roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<Long> roleIds) {
        if(CollUtil.isEmpty(roleIds)){
            return false;
        }
        RoleServiceImpl self = getSelf();
        return roleIds.stream().anyMatch(roleId -> {
            RoleDO roleDO = self.getRoleFromCache(roleId);
            return roleDO!=null && RoleCodeEnum.isSuperAdmin(roleDO.getCode());
        });
    }

    /**
     * 获取自身的bean对象 使得spring aop可以发挥作用
     */
    private RoleServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
