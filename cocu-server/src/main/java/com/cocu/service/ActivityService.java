package com.cocu.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cocu.vo.ActivityVO;
import com.cocu.vo.AuditVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
public interface ActivityService extends IService<Activity> {

    IPage<ActivityVO> listSmart(Page<ActivityVO> page, Wrapper wrapper);

    void audit(AuditVO auditVO);
}
