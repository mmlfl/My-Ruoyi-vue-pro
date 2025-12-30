package cn.iocoder.lfl.module.system.dal.mysql.dept;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.PostDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Collection;
import java.util.List;

public interface PostMapper extends BaseMapperX<PostDO> {
    default PostDO selectByName(String name) {
        return selectOne(PostDO::getName, name);
    }

    default PostDO selectByCode(String code) {
        return selectOne(PostDO::getCode, code);
    }

    default PageResult<PostDO> selectPage(PostPageReqVO reqVO) {
        return selectPage(reqVO,new LambdaQueryWrapperX<PostDO>()
                .likeIfPresent(PostDO::getName, reqVO.getName())
                .likeIfPresent(PostDO::getCode, reqVO.getCode())
                .eqIfPresent(PostDO::getStatus, reqVO.getStatus())
        );
    }

    default List<PostDO> selectList(Collection<Long> ids,Collection<Integer> status){
        return selectList(new LambdaQueryWrapperX<PostDO>()
                .inIfPresent(PostDO::getId,ids)
                .inIfPresent(PostDO::getStatus,status)
        );
    }
}
