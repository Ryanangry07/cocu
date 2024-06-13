package com.cocu.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.UserApplyActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cocu.vo.ApplicationVO;
import com.cocu.vo.AuditVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-06-10
 */
public interface UserApplyActivityService extends IService<UserApplyActivity> {

    IPage<ApplicationVO> listSmart(Page<ApplicationVO> page, Wrapper wrapper);

    void audit(AuditVO auditVO);

    List<Integer> listActivityIdsByUserId(Long userId);

}
