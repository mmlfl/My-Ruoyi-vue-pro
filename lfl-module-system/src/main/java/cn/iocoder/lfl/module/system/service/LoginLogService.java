package cn.iocoder.lfl.module.system.service;

import cn.iocoder.lfl.module.system.pojo.DO.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginLogService extends IService<SysLoginLog> {
    public void createLoginLog(SysLoginLog sysLoginLog);
}
