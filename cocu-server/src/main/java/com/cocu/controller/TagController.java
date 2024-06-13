package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.Tag;
import com.cocu.service.TagService;
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
@RequestMapping("/tag")
public class TagController {

    @Resource
    public TagService tagService;


    @PostMapping("/save")
    public Result save(@RequestBody Tag tag){
        if(StringUtils.isBlank(tag.getAvatar())){
            tag.setAvatar(Constant.DEFAULT_ACTIVITY_TAG_AVATAR);
        }
        return tagService.save(tag) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Tag tag){
        return tagService.updateById(tag) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return tagService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        return Result.success(tagService.list(), "Get tag list successfully!");
    }




    @PostMapping("/listPage")
    //public List<Tag> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");

        Page<Tag> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like(Tag::getName, queryName);
        }

        // result
        IPage<Tag> result = tagService.page(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }



}
