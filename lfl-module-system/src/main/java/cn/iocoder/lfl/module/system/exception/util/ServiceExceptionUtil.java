package cn.iocoder.lfl.module.system.exception.util;

import cn.iocoder.lfl.framework.common.exception.enums.ErrorCodeEnum;
import cn.iocoder.lfl.module.system.exception.ServiceException;

public class ServiceExceptionUtil {

    public static ServiceException exception(ErrorCodeEnum errorCodeEnum){
        return new ServiceException(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }
    public static ServiceException exception(Integer code, String message){
        return new ServiceException(code, message);
    }
}
