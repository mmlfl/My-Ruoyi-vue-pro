package cn.iocoder.lfl.module.system.service;

import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginLogService extends IService<LoginLogDO> {
    public void createLoginLog(LoginLogDO loginLogDO);
}
