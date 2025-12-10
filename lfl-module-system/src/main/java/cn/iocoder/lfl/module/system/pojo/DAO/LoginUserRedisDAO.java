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
        String tokenKey = formatTokenKey(token);
        String activeTokenKey = formateActiveTokenKey(loginuser.getId(),loginuser.getUserType());

        String oldToken = stringRedisTemplate.opsForValue().get(activeTokenKey);
        if(oldToken!=null && !oldToken.equals(token)){
            stringRedisTemplate.delete(formatTokenKey(oldToken));
            stringRedisTemplate.delete(activeTokenKey);
        }

        stringRedisTemplate.opsForValue().set(tokenKey, JsonUtils.toJsonString(loginuser),30,TimeUnit.MINUTES);
        stringRedisTemplate.opsForValue().set(activeTokenKey,token,30,TimeUnit.MINUTES);
    }

    public LoginUser get(String token){
        String key = formatTokenKey(token);
        return JsonUtils.parseObject(stringRedisTemplate.opsForValue().get(key), LoginUser.class);
    }

    public void delete(String token){
        String key = formatTokenKey(token);
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取用户token的key 这是存储用户信息的key
     * @param token
     * @return
     */
    public String formatTokenKey(String token){
        return String.format(RedisConstants.LOGIN_USER_KEY,token);
    }

    /**
     * 根据用户id和用户的登录状态 获取用户激活的token 以实现多端互踢
     * @param id
     * @param UserType
     * @return
     */
    public String formateActiveTokenKey(Long id,Integer UserType){
        return String.format(RedisConstants.USER_ACTIVE_TOKEN_KEY,id,UserType);
    }
}
