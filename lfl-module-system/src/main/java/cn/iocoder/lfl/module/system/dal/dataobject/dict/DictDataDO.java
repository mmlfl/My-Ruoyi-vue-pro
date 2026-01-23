package cn.iocoder.lfl.module.system.dal.dataobject.dict;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("system_dict_data")
@Data
public class DictDataDO extends BaseDO {
    private Long id;

    private Integer sort;

    private String label;

    private String value;

    private String dictType;

    private Integer status;

    private String colorType;

    private String cssClass;

    private String remark;
}
