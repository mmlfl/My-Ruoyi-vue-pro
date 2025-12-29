package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapperX<RoleDO> {
    default PageResult<RoleDO> selectPage(RolePageReqVO reqVO){
        return selectPage(reqVO,new LambdaQueryWrapperX<RoleDO>()
                .likeIfPresent(RoleDO::getName,reqVO.getName())
                .likeIfPresent(RoleDO::getCode, reqVO.getCode())
                .eqIfPresent(RoleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RoleDO::getType, reqVO.getType())
        );
    }
}
