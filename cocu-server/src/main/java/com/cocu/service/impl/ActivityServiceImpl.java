package com.cocu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Activity;
import com.cocu.mapper.ActivityMapper;
import com.cocu.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cocu.vo.ActivityVO;
import com.cocu.vo.AuditVO;
import org.springframework.stereotype.Service;

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
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {


    @Resource
    public ActivityMapper activityMapper;

    @Override
    public IPage<ActivityVO> listSmart(Page<ActivityVO> page, Wrapper wrapper) {
        return activityMapper.listSmart(page, wrapper);
    }

    @Override
    public void audit(AuditVO auditVO) {
        //Activity activity = activityMapper.selectById(auditVO.getActivityId());

        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", auditVO.getActivityId());
        wrapper.set("audit_status", auditVO.getAuditStatus());
        wrapper.set("audit_content", auditVO.getAuditContent());
        wrapper.set("is_recommend", auditVO.isRecommend());
        activityMapper.update(null, wrapper);
    }
}
