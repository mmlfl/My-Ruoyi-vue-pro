package cn.iocoder.lfl.module.system.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.user.vo.user.UserPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.lfl.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cn.iocoder.lfl.framework.common.exception.util.ServiceExceptionUtil.exception;
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

    @Override
    public void updateUser(UserSaveReqVO updateReqVO) {

        //1. 校验正确性
        AdminUserDO user = validateUserForCreateOrUpdate(updateReqVO.getId(),updateReqVO.getUsername(),
                updateReqVO.getEmail(), updateReqVO.getMobile(),updateReqVO.getDeptId(),updateReqVO.getPostIds());
        //2. 更新数据库
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        AdminUserDO user = userMapper.selectById(id);
        if(Objects.isNull(user)){
            throw exception(USER_NOT_EXISTS);
        }
        //删除用户
        userMapper.deleteById(id);
    }

    @Override
    public void deleteUserList(List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return;
        }
        //批量删除用户
        userMapper.deleteByIds(ids);
    }




    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(AdminUserDO.builder().id(id).loginIp(loginIp).loginDate(LocalDateTime.now()).build());
    }

    @Override
    public AdminUserDO getUser(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        //1.校验是否存在用户
        validateUserExists(id);
        //2.更新用户
        AdminUserDO userDO = AdminUserDO.builder()
                .id(id)
                .password(encodePassword(password))
                .build();
        userMapper.updateById(userDO);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // 1.校验用户是否存在
        validateUserExists(id);
        // 2.更新用户
        AdminUserDO updateObj = new AdminUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        userMapper.updateById(updateObj);
    }

    @Override
    public PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO) {

        return userMapper.selectPage(reqVO);
    }

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword,encodedPassword);
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
