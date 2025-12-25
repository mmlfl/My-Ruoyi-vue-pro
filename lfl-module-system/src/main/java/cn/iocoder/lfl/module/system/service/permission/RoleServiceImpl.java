package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.permission.vo.role.RolePageReqVO;
import cn.iocoder.lfl.module.system.controller.permission.vo.role.RoleRespVO;
import cn.iocoder.lfl.module.system.controller.permission.vo.role.RoleSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.RoleMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisConstants;
import cn.iocoder.lfl.module.system.enums.permission.RoleCodeEnum;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;

@Service
public class RoleServiceImpl implements RoleService{

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

    @Override
    public Long createRole(RoleSaveReqVO reqVO) {
        RoleDO roleDO = BeanUtils.toBean(reqVO, RoleDO.class);
        roleMapper.insert(roleDO);
        return roleDO.getId();
    }

    @Override
    public void deleteRole(Long id) {
        validateRoleExisis(id);
        roleMapper.deleteById(id);
    }

    @Override
    public void deleteRoleList(List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return;
        }
        roleMapper.deleteByIds(ids);
    }

    @Override
    public void updateRole(RoleSaveReqVO reqVO) {
        //1. 校验角色是否存在
        validateRoleExisis(reqVO.getId());
        //2. 更新角色
        RoleDO roleDO = BeanUtils.toBean(reqVO, RoleDO.class);
        roleMapper.updateById(roleDO);
    }

    @Override
    public void updateRoleStatus(Long id, Integer status) {
        //1.校验角色是否存在
        validateRoleExisis(id);
        //2.更新角色状态
        RoleDO role = new RoleDO();
        role.setId(id);
        role.setStatus(status);
        roleMapper.updateById(role);
    }

    @Override
    public RoleRespVO getRole(Long id) {
        //1.校验角色是否存在
        RoleDO role = validateRoleExisis(id);
        //2.获取角色
        RoleRespVO respVO = BeanUtils.toBean(role, RoleRespVO.class);
        return respVO;
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }

    private RoleDO validateRoleExisis(Long id){
        RoleDO role = roleMapper.selectById(id);
        if(role == null){
            throw exception(ErrorCodeConstants.ROLE_NOT_EXISTS);
        }
        return role;
    }

    /**
     * 获取自身的bean对象 使得spring aop可以发挥作用
     */
    private RoleServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
