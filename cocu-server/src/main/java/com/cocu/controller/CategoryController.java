package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.Category;
import com.cocu.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {


    @Resource
    public CategoryService categoryService;


    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        if(StringUtils.isBlank(category.getAvatar())){
            category.setAvatar(Constant.DEFAULT_ACTIVITY_CATEGORY_AVATAR);
        }
        return categoryService.save(category) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Category category){
        return categoryService.updateById(category) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return categoryService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        return Result.success(categoryService.list(), "Get category list successfully!");
    }


    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");

        Page<Category> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like(Category::getName, queryName);
        }

        // result
        IPage<Category> result = categoryService.page(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

}
