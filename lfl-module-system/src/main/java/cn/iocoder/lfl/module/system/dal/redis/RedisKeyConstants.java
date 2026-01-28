package cn.iocoder.lfl.module.system.dal.redis;

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
}
