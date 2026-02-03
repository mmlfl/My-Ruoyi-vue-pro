package cn.iocoder.lfl.framework.common.exception;

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
