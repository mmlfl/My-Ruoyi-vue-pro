package com.lfl.yudao.server.pojo;

import com.lfl.yudao.server.exception.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(Integer code, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCodeEnum errorCodeEnum){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(errorCodeEnum.getCode());
        result.setMsg(errorCodeEnum.getMessage());
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

}
