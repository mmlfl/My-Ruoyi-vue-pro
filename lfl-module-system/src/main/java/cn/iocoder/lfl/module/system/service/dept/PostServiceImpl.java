package cn.iocoder.lfl.module.system.service.dept;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.lfl.module.system.dal.mysql.dept.PostMapper;
import cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;

@Service
public class PostServiceImpl implements PostService{
    @Resource
    private PostMapper postMapper;

    @Override
    public Long createPost(PostSaveReqVO reqVO) {
        //1.校验参数
        validatePostForCreateOrUpdate(null,reqVO.getName(),reqVO.getCode());
        //2.插入数据
        PostDO post = BeanUtils.toBean(reqVO, PostDO.class);
        postMapper.insert(post);
        return post.getId();
    }

    @Override
    public void updatePost(PostSaveReqVO reqVO) {
        //1.校验参数
        validatePostForCreateOrUpdate(reqVO.getId(),reqVO.getName(),reqVO.getCode());
        //2.更新数据
        PostDO post = BeanUtils.toBean(reqVO, PostDO.class);
        postMapper.updateById(post);
    }

    @Override
    public void deletePost(Long id) {
        //1.校验自己是否存在
        validatePostExists(id);
        //2.删除数据
        postMapper.deleteById(id);
    }

    @Override
    public void deletePostList(List<Long> ids) {
        postMapper.deleteByIds(ids);
    }

    @Override
    public PostDO getPost(Long id) {
        validatePostExists(id);
        return postMapper.selectById(id);
    }

    @Override
    public PageResult<PostDO> getPostPageList(PostPageReqVO reqVO) {
        return postMapper.selectPage(reqVO);
    }

    @Override
    public List<PostDO> getPostList(Collection<Long> ids, Collection<Integer> status) {
            return postMapper.selectList(ids,status);
    }

    private void validatePostForCreateOrUpdate(Long id,String name,String code) {
        //1.校验自己是否存在
        validatePostExists(id);
        //2.校验名称唯一
        validatePostNameUnique(id,name);
        //3.校验编码唯一
        validatePostCodeUnique(id,code);
    }

    private void validatePostExists(Long id) {
        if(id == null){
            return;
        }
        if(postMapper.selectById(id) == null){
            throw exception(ErrorCodeConstants.POST_NOT_FOUND);
        }
    }

    private void validatePostCodeUnique(Long id, String code) {
        PostDO postDO = postMapper.selectByCode(code);
        if(postDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.POST_CODE_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,postDO.getId())){
            throw exception(ErrorCodeConstants.POST_CODE_DUPLICATE);
        }
    }

    private void validatePostNameUnique(Long id, String name) {
        PostDO postDO = postMapper.selectByName(name);
        if(postDO == null){
            return;
        }
        if(id == null){
            throw exception(ErrorCodeConstants.POST_NAME_DUPLICATE);
        }
        if(ObjectUtil.notEqual(id,postDO.getId())){
            throw exception(ErrorCodeConstants.POST_NAME_DUPLICATE);
        }
    }
}
