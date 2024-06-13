package com.cocu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cocu.entity.ClubMember;
import com.cocu.mapper.ClubMemberMapper;
import com.cocu.service.ClubMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@Service
public class ClubMemberServiceImpl extends ServiceImpl<ClubMemberMapper, ClubMember> implements ClubMemberService {

    @Resource
    private ClubMemberMapper clubMemberMapper;

    @Override
    public List<ClubMember> exportClubMemberExcel(Long clubId) {
        LambdaQueryWrapper<ClubMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClubMember::getId, clubId);
        return clubMemberMapper.selectList(wrapper);
    }
}
