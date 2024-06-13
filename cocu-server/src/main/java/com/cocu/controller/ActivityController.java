package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.Activity;
import com.cocu.entity.Club;
import com.cocu.entity.UserApplyActivity;
import com.cocu.service.ActivityService;
import com.cocu.service.UserApplyActivityService;
import com.cocu.vo.ActivityVO;
import com.cocu.vo.AuditVO;
import com.cocu.vo.ClubVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    public ActivityService activityService;

    @Resource
    public UserApplyActivityService userApplyActivityService;

    @PostMapping("/save")
    public Result save(@RequestBody Activity activity){
        if(StringUtils.isBlank(activity.getAvatar())){
            activity.setAvatar(Constant.DEFAULT_ACTIVITY_AVATAR);
        }
        activity.setContentId(1L);
        activity.setAuditStatus("0");
        return activityService.save(activity) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Activity activity){
        return activityService.updateById(activity) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return activityService.removeById(id) ? Result.success() : Result.fail();
    }


    @GetMapping("/getById")
    public Result getById(Long id){
        Activity activity = activityService.getById(id);
        return Result.success(activity, "Query successfully!");
    }

    @PostMapping("/audit")
    public Result audit(@RequestBody AuditVO auditVO){
        activityService.audit(auditVO);
        return Result.success();
    }


    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");
        String queryAuditStatus = (String) pageParam.getData().get("auditStatus");

        Page<ActivityVO> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like("cc.name", queryName);
        }
        // query condition
        if(StringUtils.isNotBlank(queryAuditStatus)){
            wrapper.eq("audit_status", queryAuditStatus);
        }

        // result
        IPage<ActivityVO> result = activityService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/getRecommendActivities")
    public Result getRecommendActivities(){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_status", 1);  // audit approved
        wrapper.orderByDesc("views");
        wrapper.last("LIMIT 8");


        List<Activity> list = activityService.list(wrapper);
        return Result.success(list, "Get recommend activities successfully!");
    }


    @GetMapping("/getTopActivities")
    public Result getTopActivities(){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_status", 1);  // audit approved
        wrapper.orderByDesc("views");
        wrapper.last("LIMIT 8");


        List<Activity> list = activityService.list(wrapper);
        return Result.success(list, "Get top activities successfully!");
    }

    @GetMapping("/getLatestActivities")
    public Result getLatestActivities(){
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_status", 1);  // audit approved
        wrapper.orderByDesc("create_date");
        wrapper.last("LIMIT 8");


        List<Activity> list = activityService.list(wrapper);
        return Result.success(list, "Get latest activities successfully!");
    }

    @GetMapping("/myActivity")
    public Result getMyActivity(Long userId){
        List<Integer> activityIds = userApplyActivityService.listActivityIdsByUserId(userId);
        List<Activity> list;
        if(activityIds.size() > 0){
            list = activityService.listByIds(activityIds);
        }else{
            list = new ArrayList<>();
        }
        return Result.success(list, "Get my activities successfully!");
    }



}
