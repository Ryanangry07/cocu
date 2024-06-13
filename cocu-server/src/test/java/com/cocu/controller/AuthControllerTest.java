package com.cocu.controller;


import com.cocu.common.Result;
import com.cocu.entity.Menu;
import com.cocu.entity.User;
import com.cocu.service.MenuService;
import com.cocu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    public UserService userService;

    @Autowired
    public MenuService menuService;

    @Test
    public void testLogin(){

        String email = "123413";
        String password = "12341";

        // get user by query account and password
        List<User> userList = userService.lambdaQuery()
                .eq(User::getEmail, email)
                .eq(User::getPassword, password)
                .list();

        // query user menu
        if(userList.size() > 0){
            User userQuery = userList.get(0);
            List menuList = menuService.lambdaQuery()
                    .like(Menu::getMenuRight, userQuery.getRoleId())
                    .list();
            // result hashmap
            HashMap result = new HashMap(){{
                put("user", userQuery);
                put("menuList", menuList);
            }};
            System.out.println(result.toString());
        }
    }

    @Test
    public void findByEmail(){
        String email = "S2022048@siswa.um.edu.my";
        List<User> list = userService.lambdaQuery().eq(User::getEmail, email).list();
        System.out.println(list.get(0));
    }

    @Test
    public void findByEmail2(){
        String email = "";
        List<User> list = userService.lambdaQuery().eq(User::getEmail, email).list();
        System.out.println(list.get(0));
    }


    @Test
    public void findByEmail3(){
        String email = "132";
        List<User> list = userService.lambdaQuery().eq(User::getEmail, email).list();
        System.out.println(list.get(0));
    }


}
