package cn.iocoder.lfl.module.system.service.logger;

import cn.iocoder.lfl.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;

public interface LoginLogService {
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);
}
