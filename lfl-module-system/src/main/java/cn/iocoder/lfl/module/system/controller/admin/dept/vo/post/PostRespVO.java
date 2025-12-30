package cn.iocoder.lfl.module.system.controller.admin.dept.vo.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 岗位信息 Response VO")
@Data
public class PostRespVO {
     @Schema(description = "岗位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
     private Long id;

     @Schema(description = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "普通员工")
     private String name;

     @Schema(description = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "user")
     private String code;

     @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
     private Integer sort;

     @Schema(description = "状态,参见 CommonStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
     private Integer status;

     @Schema(description = "备注",example = "")
     private String remark;

     @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
     private LocalDateTime createTime;
}
