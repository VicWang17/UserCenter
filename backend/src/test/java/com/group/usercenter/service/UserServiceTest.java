package com.group.usercenter.service;
import java.util.Date;

import com.group.usercenter.mapper.UserMapper;
import com.group.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testAdd(){
        User user = new User();
        user.setUserAccount("123");
        user.setGender(1);
        user.setUserPassword("sd123");
        user.setPhone("123");
        user.setEmail("123");
        boolean result = userService.save(user);
        System.out.println(user.getUserId());
        Assertions.assertTrue(result);

    }


    @Test
    void userRegister() {
        String userAccount = "hahaha";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount,userPassword,checkPassword);
        assertEquals(-1, result);
    }
}