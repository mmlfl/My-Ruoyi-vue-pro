package cn.iocoder.lfl.module.system.exception;

import cn.iocoder.lfl.framework.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class ServiceException extends RuntimeException{
    private Integer code;
    private String message;

    public ServiceException(ErrorCode errorCodeEnum){
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }
}
