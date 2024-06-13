package com.cocu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.UserApplyClub;
import com.cocu.mapper.UserApplyClubMapper;
import com.cocu.service.UserApplyClubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cocu.vo.ApplicationVO;
import com.cocu.vo.AuditVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-10
 */
@Service
public class UserApplyClubServiceImpl extends ServiceImpl<UserApplyClubMapper, UserApplyClub> implements UserApplyClubService {

    @Resource
    public UserApplyClubMapper userApplyClubMapper;

    @Override
    public IPage<ApplicationVO> listSmart(Page<ApplicationVO> page, Wrapper wrapper) {
        return userApplyClubMapper.listSmart(page, wrapper);
    }



    @Override
    public void audit(AuditVO auditVO) {
        //Activity activity = activityMapper.selectById(auditVO.getActivityId());

        UpdateWrapper<UserApplyClub> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", auditVO.getActivityId());
        wrapper.set("audit_status", auditVO.getAuditStatus());
        wrapper.set("audit_content", auditVO.getAuditContent());
        userApplyClubMapper.update(null, wrapper);
    }

    @Override
    public List<Integer> listClubIdsByUserId(Long userId) {
        return userApplyClubMapper.listClubIdsByUserId(userId);
    }
}
