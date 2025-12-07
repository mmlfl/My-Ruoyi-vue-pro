package com.lfl.yudao.server.exception.util;

import com.lfl.lfl.framework.common.exception.enums.ErrorCodeEnum;
import com.lfl.yudao.server.exception.ServiceException;

public class ServiceExceptionUtil {

    public static ServiceException exception(ErrorCodeEnum errorCodeEnum){
        return new ServiceException(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }
    public static ServiceException exception(Integer code, String message){
        return new ServiceException(code, message);
    }
}
