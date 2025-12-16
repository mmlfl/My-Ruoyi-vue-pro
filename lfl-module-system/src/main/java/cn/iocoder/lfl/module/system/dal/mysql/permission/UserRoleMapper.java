package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.UserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRoleDO> {
    default List<UserRoleDO> selectListByUserId(Long userId){
        return selectList(UserRoleDO::getUserId,userId);
    }
}
