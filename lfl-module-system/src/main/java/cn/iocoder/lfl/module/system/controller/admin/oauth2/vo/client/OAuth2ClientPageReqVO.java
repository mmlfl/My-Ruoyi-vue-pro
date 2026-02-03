package cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class OAuth2ClientPageReqVO extends PageParam {
    private String name;

    private Integer status;
}
