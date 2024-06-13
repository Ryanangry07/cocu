package com.cocu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.common.Result;
import com.cocu.entity.Club;
import com.cocu.service.ClubService;
import com.cocu.vo.ClubVO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClubControllerTest {

    @Autowired
    public ClubService clubService;

    @Test
    public void testListSmart(){
        // get param
        String queryName = "t";
        String queryTypeId = "1";

        Page<ClubVO> page = new Page<>(1, 10);
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
        System.out.println(result.getRecords());

    }


    @Test
    public void testListSmart1(){
        // get param
        String queryName = "keywords here";
        String queryTypeId = "12";

        Page<ClubVO> page = new Page<>(1, 10);
        QueryWrapper<Club> wrapper = new QueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryName)){
            wrapper.like("cc.name", queryName);
        }

        // result
        IPage<ClubVO> result = clubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        System.out.println(result.getRecords());

    }



    @Test
    public void testListSmart2(){
        // get param
        String queryName = "keywords here";
        String queryTypeId = "1";

        Page<ClubVO> page = new Page<>(1, 10);
        QueryWrapper<Club> wrapper = new QueryWrapper<>();

        // query condition
        if(StringUtils.isNotBlank(queryTypeId)){
            wrapper.eq("type_id", queryTypeId);
        }

        // result
        IPage<ClubVO> result = clubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        System.out.println(result.getRecords());

    }


    @Test
    public void testListSmart3(){
        // get param
        String queryName = "keywords here";
        String queryTypeId = "1";

        Page<ClubVO> page = new Page<>(1, 10);
        QueryWrapper<Club> wrapper = new QueryWrapper<>();

        // result
        IPage<ClubVO> result = clubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        System.out.println(result.getRecords());

    }


    @Test
    public void testListSmart4(){
        // get param

        Page<ClubVO> page = new Page<>(1, 10);
        QueryWrapper<Club> wrapper = new QueryWrapper<>();

        // result
        IPage<ClubVO> result = clubService.listSmart(page, wrapper);
        //System.out.println("Total count:" + result.getTotal());
        System.out.println(result.getRecords());

    }


}
