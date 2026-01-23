package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 字典数据 Response VO")
@Data
public class DictDataRespVO {
    private Long id;

    private String label;

    private String value;

    private Integer sort;

    private Integer status;

    private String colorType;

    private String cssClass;

    private String remark;

    private LocalDateTime createTime;
}
