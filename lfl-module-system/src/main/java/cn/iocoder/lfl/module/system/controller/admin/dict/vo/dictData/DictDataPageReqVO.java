package cn.iocoder.lfl.module.system.controller.admin.dict.vo.dictData;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 字典数据分页请求")
@Data
public class DictDataPageReqVO extends PageParam {
    private String dictType;

    private String label;

    private Integer status;
}
