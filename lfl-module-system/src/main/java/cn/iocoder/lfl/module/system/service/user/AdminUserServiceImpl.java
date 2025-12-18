package cn.iocoder.lfl.module.system.service.user;

import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.user.vo.user.UserSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.lfl.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

import static cn.iocoder.lfl.module.system.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.*;
@Service
public class AdminUserServiceImpl implements AdminUserService{

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Long createUser(UserSaveReqVO createReqVO) {
        //1. 校验正确性
        validateUserForCreateOrUpdate(null,createReqVO.getUsername(),
                createReqVO.getEmail(), createReqVO.getMobile(),createReqVO.getDeptId(),createReqVO.getPostIds());
        //2. 先转换为user,然后存入数据库中
        AdminUserDO user = BeanUtils.toBean(createReqVO, AdminUserDO.class);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus());//默认开启
        user.setPassword(encodePassword(user.getPassword()));
        userMapper.insert(user);

        return user.getId();
    }


    private AdminUserDO validateUserForCreateOrUpdate(Long id, String username, String email, String mobile, Long deptId, Set<Long> postIds){
        AdminUserDO user = validateUserExists(id);
        validateUsernameUnique(id,username);
        validateEmailUnique(id,email);
        validateMobileUnique(id,mobile);

        return user;
    }

    private void validateMobileUnique(Long id, String mobile) {
        if(!StringUtils.hasText(mobile)){
            return;
        }
        AdminUserDO user = userMapper.selectOne(AdminUserDO::getMobile, mobile);
        if(user == null){
            return;
        }
        if(id == null){
            throw exception(USER_MOBILE_EXISTS);
        }else if(!user.getId().equals(id)){
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    private void validateEmailUnique(Long id, String email) {
        if(!StringUtils.hasText(email)){
            return;
        }
        AdminUserDO user = userMapper.selectOne(AdminUserDO::getEmail, email);
        if(user == null){
            return;
        }
        if(id == null){
            throw exception(USER_EMAIL_EXISTS);
        }else if(!user.getId().equals(id)){
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    private void validateUsernameUnique(Long id, String username) {
        if(!StringUtils.hasText(username)){
            return;
        }
        AdminUserDO user = userMapper.selectOne(AdminUserDO::getUsername, username);
        if(user == null){
            return;
        }
        if(id == null){
            throw exception(USER_USERNAME_EXISTS);
        }else if(!user.getId().equals(id)){
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    private AdminUserDO validateUserExists(Long id) {
        if(id == null){
            return null;
        }
        AdminUserDO user = userMapper.selectById(id);
        if(user == null){
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    /**
     * 加密密码
     * @param password
     * @return
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
