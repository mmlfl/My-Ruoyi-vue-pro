package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictType;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 字典类型分页 Request VO")
@Data
public class DictTypePageReqVO extends PageParam {
    @Schema(description = "字典类型名称,模糊匹配", example = "字典类型")
    private String name;

    @Schema(description = "字典类型,模糊匹配", example = "sys_common_sex")
    private String type;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

    @Schema(description = "创建时间,范围查询", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    private LocalDateTime[] createTimes;
}
