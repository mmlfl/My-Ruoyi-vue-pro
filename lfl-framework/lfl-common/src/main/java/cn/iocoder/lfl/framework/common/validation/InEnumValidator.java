package cn.iocoder.lfl.framework.common.validation;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.lfl.framework.common.core.ArrayValuable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum,Object> {

    private List<?> values;

    @Override
    public void initialize(InEnum annotation) {
        ArrayValuable<?>[] values = annotation.value().getEnumConstants();
        if(values.length == 0){
            this.values = Collections.emptyList();
        }else {
            this.values = Arrays.asList(values[0].array());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(CollUtil.isEmpty(values)){
            return true;
        }
        if(values.contains(value)){
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation();
        return false;
    }
}
