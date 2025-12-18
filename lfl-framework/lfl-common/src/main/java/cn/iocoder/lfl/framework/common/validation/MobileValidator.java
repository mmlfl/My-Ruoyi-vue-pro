package cn.iocoder.lfl.framework.common.validation;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.validation.ValidationUtil;
import cn.iocoder.lfl.framework.common.util.validation.ValidationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile,String> {
    @Override
    public void initialize(Mobile constraintAnnotation) {

    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(StrUtil.isEmpty(value)){
            return true;
        }
        return ValidationUtils.isMobile( value);
    }
}
