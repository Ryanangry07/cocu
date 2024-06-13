package com.cocu.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Club;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cocu.entity.ClubType;
import com.cocu.vo.ClubVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
public interface ClubService extends IService<Club> {

    IPage<ClubVO> listSmart(Page<ClubVO> page, Wrapper wrapper);


}
