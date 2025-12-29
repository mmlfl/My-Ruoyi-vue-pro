package cn.iocoder.lfl.module.system.dal.mysql.user;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.user.vo.user.UserPageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {

    default PageResult<AdminUserDO> selectPage(UserPageReqVO reqVO){
        return selectPage(reqVO, new LambdaQueryWrapperX<AdminUserDO>()
                .likeIfPresent(AdminUserDO::getUsername,reqVO.getUsername())
                .likeIfPresent(AdminUserDO::getMobile,reqVO.getMobile())
                .eqIfPresent(AdminUserDO::getStatus,reqVO.getStatus())
                .betweenIfPresent(AdminUserDO::getCreateTime,reqVO.getCreateTime())
                .orderByDesc(AdminUserDO::getId)
        );
    }
}
