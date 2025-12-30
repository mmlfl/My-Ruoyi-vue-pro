package cn.iocoder.lfl.module.system.service.dept;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.PostDO;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PostService {
    Long createPost(PostSaveReqVO reqVO);

    void updatePost(PostSaveReqVO reqVO);

    void deletePost(Long id);

    void deletePostList(List<Long> ids);

    PostDO getPost(Long id);

    PageResult<PostDO> getPostPageList(PostPageReqVO reqVO);

    List<PostDO> getPostList(@Nullable Collection<Long> ids,
                             @Nullable Collection<Integer> status);
}
