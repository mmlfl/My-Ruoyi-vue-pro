package cn.iocoder.lfl.framework.common.util.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import cn.iocoder.lfl.framework.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

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
