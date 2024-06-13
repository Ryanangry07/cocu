package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Constant;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.Activity;
import com.cocu.entity.Club;
import com.cocu.entity.ClubType;
import com.cocu.entity.UserApplyClub;
import com.cocu.service.ClubService;
import com.cocu.service.ClubTypeService;
import com.cocu.service.UserApplyClubService;
import com.cocu.vo.ClubVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/club")
public class ClubController {

    @Resource
    public ClubService clubService;

    @Resource
    public UserApplyClubService userApplyClubService;

    @PostMapping("/save")
    public Result save(@RequestBody Club club){
        if(StringUtils.isBlank(club.getAvatar())){
            club.setAvatar(Constant.DEFAULT_CLUB_AVATAR);
        }
        return clubService.save(club) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Club club){
        return clubService.updateById(club) ? Result.success() : Result.fail();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id){
        return clubService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        List<Club> list = clubService.list();

        return Result.success(list, "Get club list successfully!");
    }

    @GetMapping("/getById")
    public Result getById(Long id){
        Club club = clubService.getById(id);
        return Result.success(club, "Query successfully!");
    }


    @GetMapping("/listByType")
    public Result listByType(Integer typeId) {
        QueryWrapper<Club> wrapper = new QueryWrapper<>();
        if (typeId != null) {
            wrapper.eq("type_id", typeId);
        }
        List<Club> list = clubService.list(wrapper);
        return Result.success(list, "Get club list by type successfully!");
    }


    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");
        String queryTypeId = (String) pageParam.getData().get("type");

        Page<ClubVO> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Club> wrapper = new QueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like("cc.name", queryName);
        }
        // query condition
        if(StringUtils.isNotBlank(queryTypeId)){
            wrapper.eq("type_id", queryTypeId);
        }

        // result
        IPage<ClubVO> result = clubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/getRecommendClubs")
    public Result getRecommendClubs(){
        QueryWrapper<Club> wrapper = new QueryWrapper<>();
        wrapper.eq("is_recommend", true);
        List<Club> list = clubService.list(wrapper);
        return Result.success(list, "Get recommend clubs successfully!");
    }

    @GetMapping("/myClub")
    public Result getMyClub(Long userId){
        List<Integer> clubIds = userApplyClubService.listClubIdsByUserId(userId);
        List<Club> list = clubService.listByIds(clubIds);
        return Result.success(list, "Get my clubs successfully!");
    }



}
