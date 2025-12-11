package cn.iocoder.lfl.module.system.service.logger;

import cn.iocoder.lfl.module.system.dal.mysql.logger.LoginLogMapper;
import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {

    @Override
    public void createLoginLog(LoginLogDO loginLogDO) {
        boolean save = save(loginLogDO);
        if(!save){
            throw new RuntimeException("创建登录日志失败");
        }
    }
}
