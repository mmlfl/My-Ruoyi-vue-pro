package cn.iocoder.lfl.module.system.exception.util;

import cn.iocoder.lfl.framework.common.exception.ErrorCode;
import cn.iocoder.lfl.module.system.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceExceptionUtil {

    public static ServiceException exception(ErrorCode errorCodeEnum){
        return new ServiceException(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }
    public static ServiceException exception(Integer code, String message){
        return new ServiceException(code, message);
    }

    public static ServiceException exception0(Integer code,String messagePattern,Object... params){
        String message = doFormat(code,messagePattern,params);
        return new ServiceException(code, message);
    }

    public static String doFormat(int code,String messagePattern,Object... params){
        if(params == null){
            return messagePattern;
        }
        StringBuffer sbuf = new StringBuffer(messagePattern.length() + 60);
        int i = 0;
        int j;
        for(int l = 0;l<params.length;l++){
            j = messagePattern.indexOf("{}",i);
            if(j == -1){
                log.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if(i == 0){
                    return messagePattern;
                }else{
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            }else{
                sbuf.append(messagePattern.substring(i,j));
                sbuf.append(params[l]);
                i = j+2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }

}
