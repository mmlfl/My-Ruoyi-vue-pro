package cn.iocoder.lfl.module.system.controller.admin.dept.vo.post;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 岗位分页列表 Request VO")
@Data
public class PostPageReqVO extends PageParam {
    @Schema(description = "岗位名称-模糊匹配", example = "普通员工")
    private String name;

    @Schema(description = "岗位编码-模糊查询",example = "user")
    private String code;

    @Schema(description = "状态-参见 CommonStatusEnum 枚举 - 查询", example = "0")
    private Integer status;
}
