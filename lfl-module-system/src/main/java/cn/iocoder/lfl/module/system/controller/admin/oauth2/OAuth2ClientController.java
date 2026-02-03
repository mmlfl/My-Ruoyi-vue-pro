package cn.iocoder.lfl.module.system.controller.admin.oauth2;

import cn.iocoder.lfl.framework.common.pojo.CommonResult;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientRespVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.iocoder.lfl.module.system.service.oauth2.OAuth2ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "管理后台 - OAuth2 客户端")
@RestController
@RequestMapping("/system/oauth2-client")
@Validated
public class OAuth2ClientController {
    @Resource
    private OAuth2ClientService oAuth2ClientService;

    @PostMapping("/create")
    @Operation(summary = "创建 OAuth2 客户端")
    public CommonResult<Long> createOAuth2Client(@Validated @RequestBody OAuth2ClientSaveReqVO createReqVO){
        Long id = oAuth2ClientService.createOAuth2Client(createReqVO);
        return CommonResult.success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "更新 OAuth2 客户端")
    public CommonResult<Boolean> updateOAuth2Client(@Validated @RequestBody OAuth2ClientSaveReqVO updateReqVO){
        oAuth2ClientService.updateOAuth2Client(updateReqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 OAuth2 客户端")
    public CommonResult<Boolean> deleteOAuth2Client(@RequestParam("id") Long id){
        oAuth2ClientService.deleteOAuth2Client(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete-batch")
    @Operation(summary = "批量删除 OAuth2 客户端")
    public CommonResult<Boolean> deleteOAuth2ClientBatch(@RequestParam("ids") List<Long> ids){
        oAuth2ClientService.deleteOAuth2ClientBatch(ids);
        return CommonResult.success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得 OAuth2 客户端的分页")
    public CommonResult<PageResult<OAuth2ClientRespVO>> getOAuth2ClientPage(@Valid OAuth2ClientPageReqVO pageReqVO){
        PageResult<OAuth2ClientDO> pageResult = oAuth2ClientService.getOAuth2ClientPage(pageReqVO);
        return CommonResult.success(BeanUtils.toBean(pageResult,OAuth2ClientRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获得 OAuth2 客户端")
    public CommonResult<OAuth2ClientRespVO> getOAuth2Client(@RequestParam("id") Long id){
        OAuth2ClientDO oAuth2ClientDO = oAuth2ClientService.getOAuth2Client(id);
        return CommonResult.success(BeanUtils.toBean(oAuth2ClientDO,OAuth2ClientRespVO.class));
    }
}
