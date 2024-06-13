package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.Activity;
import com.cocu.entity.UserApplyClub;
import com.cocu.service.UserApplyClubService;
import com.cocu.vo.ActivityVO;
import com.cocu.vo.ApplicationVO;
import com.cocu.vo.AuditVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-10
 */
@RestController
@RequestMapping("/user-apply-club")
public class UserApplyClubController {

    @Resource
    public UserApplyClubService userApplyClubService;

    @PostMapping("/audit")
    public Result audit(@RequestBody AuditVO auditVO){
        userApplyClubService.audit(auditVO);
        return Result.success();
    }


    @PostMapping
    public Result join(@RequestBody UserApplyClub applyClub){
        // check if join club
        LambdaQueryWrapper<UserApplyClub> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserApplyClub::getUserId, applyClub.getUserId())
                .eq(UserApplyClub::getClubId, applyClub.getClubId());
        UserApplyClub one = userApplyClubService.getOne(wrapper);
        if(one != null){
            return Result.fail("You applied for this club already, no need to apply!");
        }
        // save to database
        if(userApplyClubService.save(applyClub)){
            return Result.success("Application submit successfully!");
        }
        return Result.fail("Fail to join, please try again!");
    }

    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");
        String queryGender = (String) pageParam.getData().get("gender");
        String queryAuditStatus = (String) pageParam.getData().get("auditStatus");

        Page<ApplicationVO> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<UserApplyClub> wrapper = new QueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like("cc.name", queryName);
        }
        // query condition
        if(StringUtils.isNotBlank(queryGender)){
            wrapper.eq("gender", queryGender);
        }
        // query condition
        if(StringUtils.isNotBlank(queryAuditStatus)){
            wrapper.eq("audit_status", queryAuditStatus);
        }

        // result
        IPage<ApplicationVO> result = userApplyClubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }



}
