package cn.iocoder.lfl.module.system.dal.dataobject.dict;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("system_dict_type")
@Data
public class DictTypeDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String type;

    private Integer status;

    private String remark;

    private LocalDateTime deleteTime;
}
