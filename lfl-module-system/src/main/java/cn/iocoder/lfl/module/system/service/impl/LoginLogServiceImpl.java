package cn.iocoder.lfl.module.system.service.impl;

import cn.iocoder.lfl.module.system.mapper.LoginLogMapper;
import cn.iocoder.lfl.module.system.pojo.DO.SysLoginLog;
import cn.iocoder.lfl.module.system.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, SysLoginLog> implements LoginLogService {

    @Override
    public void createLoginLog(SysLoginLog sysLoginLog) {
        boolean save = save(sysLoginLog);
        if(!save){
            throw new RuntimeException("创建登录日志失败");
        }
    }
}
