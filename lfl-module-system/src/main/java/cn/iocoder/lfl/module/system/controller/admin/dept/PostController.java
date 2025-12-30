package cn.iocoder.lfl.module.system.controller.admin.dept;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostRespVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostSaveReqVO;
import cn.iocoder.lfl.module.system.controller.admin.dept.vo.post.PostSimpleRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.lfl.module.system.service.dept.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Tag(name = "管理后台 - 岗位")
@RestController
@RequestMapping("/system/post")
@Validated
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/create")
    @Operation(summary = "创建岗位")
    public CommonResult<Long> createPost(@RequestBody @Valid PostSaveReqVO reqVO){
        Long id = postService.createPost(reqVO);
        return CommonResult.success(id);
    }

    @PostMapping("/update")
    @Operation(summary = "更新岗位")
    public CommonResult<Boolean> updatePost(@RequestBody @Valid PostSaveReqVO reqVO){
        postService.updatePost(reqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除岗位")
    public CommonResult<Boolean> deletePost(@RequestParam("id") Long id){
        postService.deletePost(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除岗位")
    public CommonResult<Boolean> deletePostList(@RequestParam("ids") List<Long> ids){
        postService.deletePostList(ids);
        return CommonResult.success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得岗位详细信息")
    public CommonResult<PostRespVO> getPost(@RequestParam("id") Long id){
        PostDO post = postService.getPost(id);
        return CommonResult.success(BeanUtils.toBean(post, PostRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询岗位列表")
    public CommonResult<PageResult<PostRespVO>> getPostPageList(PostPageReqVO reqVO){
        PageResult<PostDO> list = postService.getPostPageList(reqVO);
        return CommonResult.success(BeanUtils.toBean(list,PostRespVO.class));
    }

    @GetMapping(value = {"list-all-simple","simple-list"})
    @Operation(summary = "获得岗位精简信息列表",description = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CommonResult<List<PostSimpleRespVO>> getSimplePostList(){
        List<PostDO> postList = postService.getPostList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        //排序展示
        postList.sort(Comparator.comparing(PostDO::getSort));
        return CommonResult.success(BeanUtils.toBean(postList, PostSimpleRespVO.class));
    }

}
