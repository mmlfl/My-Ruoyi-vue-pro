package cn.iocoder.lfl.framework.common.util.servlet;

import cn.hutool.extra.servlet.ServletUtil;
import cn.iocoder.lfl.framework.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 获取请求
     * @return
     */
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //判断当前是否是web的请求
        if(!(requestAttributes instanceof ServletRequestAttributes)){
            return null;
        }
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    /**
     * 获取UserAgent
     * @return
     */
    public static String getUserAgent(){
        HttpServletRequest request = getRequest();
        if(request == null){
            return null;
        }
        return getUserAgent(request);
    }

    private static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null? ua : "";
    }

    /**
     * 获取用户IP
     * @return
     */
    public static String getClientIP(){
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return ServletUtil.getClientIP(request);
    }


}
