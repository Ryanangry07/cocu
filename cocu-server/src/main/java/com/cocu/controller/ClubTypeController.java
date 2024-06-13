package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.ClubType;
import com.cocu.service.ClubTypeService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@RestController
@RequestMapping("/club-type")
public class ClubTypeController {

    @Resource
    public ClubTypeService clubTypeService;

    @PostMapping("/save")
    public Result save(@RequestBody ClubType clubType){
        if(StringUtils.isBlank(clubType.getAvatar())){
            clubType.setAvatar(Constant.DEFAULT_USER_AVATAR);
        }
        return clubTypeService.save(clubType) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody ClubType clubType){
        return clubTypeService.updateById(clubType) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return clubTypeService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        return Result.success(clubTypeService.list(), "Get club type list successfully!");
    }



    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");

        Page<ClubType> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<ClubType> wrapper = new LambdaQueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like(ClubType::getName, queryName);
        }

        // result
        IPage<ClubType> result = clubTypeService.page(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

}
