package cn.iocoder.lfl.module.system.dal.redis;

import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

public interface RedisKeyConstants {

    String LOGIN_USER_KEY = "login:user:token:%s";
    String USER_ACTIVE_TOKEN_KEY = "login:user:active:%s:%s";

    String USER_ROLE_ID_LIST = "system_role_ids";
    String ROLE = "role";
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";
    String MENU_ROLE_ID_LIST = "menu_role_ids";

    /**
     * 指定部门的所有子部门编号数组的缓存
     * <p>
     * KEY 格式：dept_children_ids:{id}
     * VALUE 数据类型：String 子部门编号集合
     */
    String DEPT_CHILDREN_ID_LIST = "dept_children_ids";


    /**
     * OAuth2 客户端的缓存
     * <p>
     * KEY 格式：oauth_client:{id}
     * VALUE 数据类型：String 客户端信息
     */
    String OAUTH_CLIENT = "oauth_client";

    /**
     * 访问令牌的缓存
     * <p>
     * KEY 格式：oauth2_access_token:{token}
     * VALUE 数据类型：String 访问令牌信息 {@link OAuth2AccessTokenDO}
     * <p>
     * 由于动态过期时间，使用 RedisTemplate 操作
     */
    String OAUTH2_ACCESS_TOKEN = "oauth2_access_token:%s";
}
