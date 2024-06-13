package com.cocu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Club;
import com.cocu.entity.UserApplyActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cocu.vo.ApplicationVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ryanangry
 * @since 2024-06-10
 */
public interface UserApplyActivityMapper extends BaseMapper<UserApplyActivity> {

    @Select("select u.id user_id, u.email, u.username, u.avatar, u.mobile, u.age, u.gender, c.id club_id, c.name, uaa.id, uaa.audit_status, uac.audit_content\n" +
            "from cocu_user_apply_activity uaa\n" +
            "left join cocu_user u on uaa.user_id=u.id\n" +
            "left join cocu_club c on uaa.club_id=c.id")
    IPage<ApplicationVO> listSmart(Page<ApplicationVO> page, @Param(Constants.WRAPPER) Wrapper<Club> wrapper);


    @Select("SELECT c.id FROM cocu_user_apply_activity c WHERE c.user_id = #{userId}")
    List<Integer> listActivityIdsByUserId(Long userId);

}
