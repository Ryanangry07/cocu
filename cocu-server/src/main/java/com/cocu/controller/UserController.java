package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.ClubMember;
import com.cocu.entity.User;
import com.cocu.entity.Menu;
import com.cocu.service.ClubMemberService;
import com.cocu.service.MenuService;
import com.cocu.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Resource
    private UserService userService;

    @Resource
    public MenuService menuService;

    @Resource
    public ClubMemberService clubMemberService;

    @Value("${jwt.secret}")
    private String secretKey;

    public UserController(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    @PostMapping("/profile")
    public Result getUserProfile(@RequestHeader("Authorization") String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            String email = claims.getSubject();

            List<User> userList = userService.lambdaQuery()
                    .eq(User::getEmail, email)
                    .list();

            if (userList.size() > 0) {
                User userQuery = userList.get(0);
                List<Menu> menuList = menuService.lambdaQuery()
                        .like(Menu::getMenuRight, userQuery.getRoleId())
                        .list();

                // result hashmap
                HashMap<String, Object> result = new HashMap<>();
                result.put("user", userQuery);
                result.put("menuList", menuList);

                return Result.success(result, "Profile fetched successfully!");
            } else {
                return Result.fail("User not found!");
            }
        } catch (SignatureException e) {
            return Result.fail("Invalid token!");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){

        // get user by query account and password
        List<User> userList = userService.lambdaQuery()
                .eq(User::getEmail, user.getEmail())
                .eq(User::getPassword, user.getPassword())
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
            return Result.success(result, "Login successfully!");
        }
        return Result.fail();
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        //check parameter
        if(StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())){
            return Result.fail();
        }
        //check email duplicate
        List<User> userList = userService.lambdaQuery()
                .eq(User::getEmail, user.getEmail())
                .list();
        if(userList.size() > 0){
            return Result.fail();
        }

        user.setUsername(user.getEmail());
        user.setMobile("123");
        user.setGender(1);
        user.setAge(18);
        user.setUsername(user.getEmail());
        user.setAvatar(Constant.DEFAULT_USER_AVATAR);
        user.setSalt(Constant.DEFAULT_SALT);
        user.setRoleId(3L);

        userService.save(user);
        return Result.success();
    }


    @GetMapping("/findByEmail")
    public Result findByNo(@RequestParam String email){
        if(StringUtils.isBlank(email)){
            return Result.fail();
        }
        List<User> list = userService.lambdaQuery().eq(User::getEmail, email).list();
        return list.size() > 0 ? Result.success() : Result.fail();
    }


    @PostMapping("/save")
    public Result save(@RequestBody User user){
        if(StringUtils.isBlank(user.getSalt())){
            user.setSalt(Constant.DEFAULT_SALT);
        }
        if(StringUtils.isBlank(user.getAvatar())){
            user.setAvatar(Constant.DEFAULT_CLUB_TYPE_AVATAR);
        }
        return userService.save(user) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        User res = userService.getById(user.getId());
        return Result.success(res, "Update user successfully!");
    }


    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return userService.removeById(id) ? Result.success() : Result.fail();
    }


    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");
        String queryGender = (String) pageParam.getData().get("gender");
        String queryRoleId = (String) pageParam.getData().get("roleId");
        String queryClubId = (String) pageParam.getData().get("clubId");

        Page<User> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like(User::getUsername, queryName);
        }
        if(StringUtils.isNotBlank(queryGender)){
            wrapper.eq(User::getGender, queryGender);
        }
        if(StringUtils.isNotBlank(queryRoleId)){
            wrapper.eq(User::getRoleId, queryRoleId);
        }
        if(StringUtils.isNotBlank(queryClubId)){
            List<ClubMember> list =
                    clubMemberService.list(new LambdaQueryWrapper<ClubMember>().eq(ClubMember::getClubId, queryClubId));
            List<Long> idList = list.stream()
                    .map(ClubMember::getUserId)
                    .collect(Collectors.toList());
            if(idList.size() > 0){
                wrapper.in(User::getId, idList);
            }else{
                return Result.success();
            }
        }

        // result
        IPage<User> result = userService.page(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

}
