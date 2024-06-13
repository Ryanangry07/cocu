package com.cocu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Club;
import com.cocu.entity.ClubType;
import com.cocu.mapper.ClubMapper;
import com.cocu.service.ClubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cocu.vo.ClubVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements ClubService {

    @Resource
    public ClubMapper clubMapper;

    @Override
    public IPage<ClubVO> listSmart(Page<ClubVO> page, Wrapper wrapper) {
        return clubMapper.listSmart(page, wrapper);
    }
}
