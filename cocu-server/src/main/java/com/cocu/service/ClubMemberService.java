package com.cocu.service;

import com.cocu.entity.ClubMember;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
public interface ClubMemberService extends IService<ClubMember> {

    List<ClubMember> exportClubMemberExcel(Long clubId);

}
