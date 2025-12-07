package com.lfl.lfl.framework.common.util.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lfl.lfl.framework.common.util.json.JsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.reflect.Java15ReflectionBasedReferenceTypeDelegate;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ServletUtils {

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object   对象，会序列化成 JSON 字符串
     */
    public static void writeJSON(HttpServletResponse response, Object object){
            String content = JsonUtils.toJsonString(object);
        ServletUtil.write(response,content, MediaType.APPLICATION_JSON_VALUE);
    }
}
