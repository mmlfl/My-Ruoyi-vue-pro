package cn.iocoder.lfl.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.iocoder.lfl.module.system.pojo.DO.User;
import cn.iocoder.lfl.module.system.pojo.DTO.LoginDTO;
import cn.iocoder.lfl.module.system.pojo.VO.LoginVO;

public interface UserService extends IService<User> {
    LoginVO login(LoginDTO loginDTO);
}
