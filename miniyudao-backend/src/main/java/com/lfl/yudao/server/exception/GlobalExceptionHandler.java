package com.lfl.yudao.server.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lfl.yudao.server.pojo.CommonResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.lfl.yudao.server.exception.ErrorCodeEnum.*;

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
            return CommonResult.error(BADREQUEST);
        }
        return CommonResult.error(BADREQUEST.getCode(),errorMessage);
    }
}
