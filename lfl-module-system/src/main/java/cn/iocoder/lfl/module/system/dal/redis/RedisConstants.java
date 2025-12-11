package cn.iocoder.lfl.module.system.dal.redis;

public interface RedisConstants {

    String LOGIN_USER_KEY = "login:user:token:%s";
    String USER_ACTIVE_TOKEN_KEY = "login:user:active:%s:%s";
}
