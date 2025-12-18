package cn.iocoder.lfl.framework.common.util.validation;


import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class ValidationUtils {
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[0,1,4-9])|(?:5[0-3,5-9])|(?:6[2,5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[0-3,5-9]))\\d{8}$");



    public static boolean isMobile(String mobile){
        return StringUtils.hasText(mobile)&&
                MOBILE_PATTERN.matcher(mobile).matches();
    }
}
