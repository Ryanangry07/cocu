package com.cocu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.PageParam;
import com.cocu.common.Result;
import com.cocu.entity.ClubMessageBoard;
import com.cocu.entity.ClubType;
import com.cocu.entity.User;
import com.cocu.service.ClubMessageBoardService;
import com.cocu.service.UserService;
import com.cocu.vo.ClubMessageBoardVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/club-message-board")
public class ClubMessageBoardController {

    @Resource
    private ClubMessageBoardService clubMessageBoardService;

    @Resource
    private UserService userService;


    @PostMapping("/save")
    public Result save(@RequestBody ClubMessageBoard clubMessageBoard){
        return clubMessageBoardService.save(clubMessageBoard) ? Result.success() : Result.fail();
    }



    @PostMapping("/listPage")
    //public List<User> listPage(@RequestBody HashMap map){}
    // (Integer)map.get("pageSize");↑
    // pageParam.getPageSize();↓
    public Result listPage(@RequestBody PageParam pageParam){
        // get param
        String queryName = (String) pageParam.getData().get("name");

        Page<ClubMessageBoard> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        LambdaQueryWrapper<ClubMessageBoard> wrapper = new LambdaQueryWrapper<>();

        // query condition
        if (StringUtils.isNotBlank(queryName)) {
            wrapper.apply("CONCAT(username, content) LIKE {0}", "%" + queryName + "%");
        }
        // order by create date
        wrapper.orderByDesc(ClubMessageBoard::getCreateDate);

        // result
        List<ClubMessageBoardVO> finalResult = new ArrayList<>();
        IPage<ClubMessageBoard> result = clubMessageBoardService.page(page, wrapper);

        //copy properties
        for(ClubMessageBoard clubMessageBoard : result.getRecords()){
            ClubMessageBoardVO clubMessageBoardVO = new ClubMessageBoardVO();
            BeanUtils.copyProperties(clubMessageBoard, clubMessageBoardVO);
            User user = userService.getById(clubMessageBoard.getUserId());
            clubMessageBoardVO.setUser(user.getEmail());
            clubMessageBoardVO.setAvatar(user.getAvatar());
            finalResult.add(clubMessageBoardVO);
        }
        //System.out.println("Total count:" + result.getTotal());
        return Result.success(finalResult, result.getTotal());
    }

}
