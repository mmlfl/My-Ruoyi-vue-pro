package cn.iocoder.lfl.module.system.service.logger;

import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.lfl.module.system.dal.mysql.logger.LoginLogMapper;
import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO bean = BeanUtils.toBean(reqDTO, LoginLogDO.class);
        loginLogMapper.insert(bean);
    }
}
