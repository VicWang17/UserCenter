package com.group.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group.usercenter.model.domain.User;
import com.group.usercenter.model.domain.request.UserLoginRequest;
import com.group.usercenter.model.domain.request.UserRegisterRequest;
import com.group.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.group.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.group.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);

    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.doLogin(userAccount, userPassword,request);

    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request){
        if (!isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);  //默认模糊查询
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> { return userService.getSaveUser(user);
        }).collect(Collectors.toList()); //todo: 理解一下
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request){
        if (!isAdmin(request)){
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    private boolean isAdmin(HttpServletRequest request){
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
