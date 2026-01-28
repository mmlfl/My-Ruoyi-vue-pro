package cn.iocoder.lfl.framework.datapermission.core.aop;

import cn.iocoder.lfl.framework.datapermission.core.annotation.DataPermission;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.LinkedList;
import java.util.List;

public class DataPermissionContextHolder {
    /**
     *使用LinkedList的原因是可能存在方法的嵌套调用
     */
    private static final TransmittableThreadLocal<LinkedList<DataPermission>> DATA_PERMISSIONS =
            TransmittableThreadLocal.withInitial(LinkedList::new);

    /**
     * 添加数据权限
     *
     * @param dataPermission 数据权限
     */
    public static void add(DataPermission dataPermission) {
        DATA_PERMISSIONS.get().addLast(dataPermission);
    }

    public static DataPermission remove(){
        DataPermission dataPermission = DATA_PERMISSIONS.get().removeLast();
        // 无元素时，清空 ThreadLocal
        if (DATA_PERMISSIONS.get().isEmpty()) {
            DATA_PERMISSIONS.remove();
        }
        return dataPermission;
    }

    /**
     * 获取数据权限
     *
     * @return 数据权限
     */
    public static DataPermission get() {
        return DATA_PERMISSIONS.get().peekLast();
    }


    /**
     * 获得所有 DataPermission
     *
     * @return DataPermission 队列
     */
    public static List<DataPermission> getAll() {
        return DATA_PERMISSIONS.get();
    }

    /**
     * 清空上下文
     *
     * 目前仅仅用于单测
     */
    public static void clear() {
        DATA_PERMISSIONS.remove();
    }

}
