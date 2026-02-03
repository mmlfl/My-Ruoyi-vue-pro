package cn.iocoder.lfl.module.system.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.lfl.framework.common.exception.enums.GlobalErrorCodeConstants;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import cn.iocoder.lfl.framework.common.pojo.CommonResult;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> handleServiceException(ServiceException e){
        return CommonResult.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = null;
        FieldError fieldError = e.getBindingResult().getFieldError();
        if(fieldError == null){
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            if(CollUtil.isNotEmpty(errors)){
                errorMessage = errors.get(0).getDefaultMessage();
            }
        }else{
            errorMessage = fieldError.getDefaultMessage();
        }
        if(StrUtil.isEmpty(errorMessage)){
            return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),errorMessage);
    }
}
