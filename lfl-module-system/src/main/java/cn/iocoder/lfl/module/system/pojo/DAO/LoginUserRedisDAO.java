package cn.iocoder.lfl.module.system.pojo.DAO;


import cn.hutool.json.JSONUtil;
import cn.iocoder.lfl.framework.common.util.json.JsonUtils;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.constants.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class LoginUserRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String token, LoginUser loginuser){
        String key = format(token);
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJsonString(loginuser),30,TimeUnit.MINUTES);
    }

    public LoginUser get(String token){
        String key = format(token);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(key), LoginUser.class);
    }

    public void delete(String token){
        String key = format(token);
        stringRedisTemplate.delete(key);
    }


    public String format(String token){
        return String.format(RedisConstants.LOGIN_USER_KEY,token);
    }
}
