package cn.iocoder.lfl.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingField implements Serializable {

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序方式
     */
    private String order;
}
