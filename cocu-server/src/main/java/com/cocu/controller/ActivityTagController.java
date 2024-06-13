package com.cocu.controller;


import com.cocu.common.Result;
import com.cocu.entity.ActivityTag;
import com.cocu.service.ActivityTagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@RestController
@RequestMapping("/activity-tag")
public class ActivityTagController {

    @Resource
    private ActivityTagService activityTagService;

    @GetMapping("/list")
    public Result list(){
        List<ActivityTag> list = activityTagService.list();
        return Result.success(list, "Get activity tag list successfully!");
    }

}
