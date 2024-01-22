package com.group.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.usercenter.mapper.UserMapper;
import com.group.usercenter.model.domain.User;
import com.group.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.group.usercenter.constant.UserConstant.USER_LOGIN_STATE;


/**
 * @author Vic Wang
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-01-19 22:21:03
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private final static String SALT = "vic";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }


        //校验账户包含特殊字符

        String validPattern = ".*[!@#$%^&*()].*";
        if (userAccount.matches(validPattern)) {
            return -1;
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //2. 加密

        // 定义加密的 SALT 值
        String SALT = "vic";
        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

        //3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getUserId();

    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }


        //校验账户包含特殊字符

        String validPattern = ".*[!@#$%^&*()].*";
        if (userAccount.matches(validPattern)) {
            return null;
        }

        //2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

        // 检验账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("User login failed, user Account cannot match userPassword");
            return null;
        }
        //3. 用户脱敏
        User safeUser = getSaveUser(user);

        //4. 记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        return safeUser;
    }

    @Override
    public User getSaveUser(User originUser){
        User safeUser = new User();
        safeUser.setUserId(originUser.getUserId());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setAvatarUrl(originUser.getAvatarUrl());
        safeUser.setGender(originUser.getGender());
        safeUser.setPhone(originUser.getPhone());
        safeUser.setEmail(originUser.getEmail());
        safeUser.setUserStatus(0);
        safeUser.setUserRole(originUser.getUserRole());
        safeUser.setCreateTime(originUser.getCreateTime());

        return safeUser;
    }


}
