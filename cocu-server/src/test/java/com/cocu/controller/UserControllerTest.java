package com.cocu.controller;

import com.cocu.entity.User;
import com.cocu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    public UserService userService;

    @Test
    public void testUserList(){
        System.out.println(userService.list());
    }

    @Test
    public void testUser(){
        System.out.println(userService.getById("1"));
    }

    @Test
    public void testUser2(){
        System.out.println(userService.getById("2"));
    }

    @Test
    public void testUse43(){
        System.out.println(userService.getById("2"));
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setEmail("12345315");
        System.out.println(userService.update(user, null));
    }


    @Test
    public void testUpdateUser2(){
        User user = new User();
        user.setEmail("12345315");
        user.setSalt("salt");
        user.setAvatar("/test");
        user.setId(100L);
        user.setGender(1);
        user.setAge(19);
        user.setMobile("12345");
        user.setPassword("1234");
        user.setRoleId(1L);
        user.setUsername("ryan");
        System.out.println(userService.update(user, null));
    }


    @Test
    public void testUpdateUser3(){
        User user = new User();
        System.out.println(userService.update(user, null));
    }
}
