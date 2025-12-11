package cn.iocoder.lfl.framework.mybatis.core.handler;

import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if(Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO){
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();
            LocalDateTime current = LocalDateTime.now();
            Long userId = SecurityFrameworkUtils.getLoginUserId();
            if(Objects.isNull(baseDO.getCreateTime())){
                baseDO.setCreateTime(current);
            }
            if(Objects.isNull(baseDO.getUpdateTime())){
                baseDO.setUpdateTime(current);
            }
            if(Objects.isNull(baseDO.getCreator()) && Objects.nonNull(userId)){
                baseDO.setCreator(userId.toString());
            }
            if(Objects.isNull(baseDO.getUpdater()) && Objects.nonNull(userId)){
                baseDO.setUpdater(userId.toString());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if(Objects.isNull(modifyTime)){
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        Object modifyUser = getFieldValByName("updater", metaObject);
        if(Objects.isNull(modifyUser)){
            Long userId = SecurityFrameworkUtils.getLoginUserId();
            if(Objects.nonNull(userId)){
                setFieldValByName("updater", userId.toString(), metaObject);
            }
        }
    }
}
