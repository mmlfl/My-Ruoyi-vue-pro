package cn.iocoder.lfl.module.system.dal.redis;

public interface RedisConstants {

    String LOGIN_USER_KEY = "login:user:token:%s";
    String USER_ACTIVE_TOKEN_KEY = "login:user:active:%s:%s";

    String USER_ROLE_ID_LIST = "system_role_ids";
    String ROLE = "role";
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";
    String MENU_ROLE_ID_LIST = "menu_role_ids";
}
