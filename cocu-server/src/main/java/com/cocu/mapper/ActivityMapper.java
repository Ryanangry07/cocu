package com.cocu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cocu.entity.Club;
import com.cocu.vo.ActivityVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
public interface ActivityMapper extends BaseMapper<Activity> {


    @Select("select a.*, u.email organizer, u.username organizerName, c.name category, club.name club from cocu_activity a\n" +
            "left join cocu_user u on a.organizer_id=u.id " +
            "left join cocu_category c on a.category_id=c.id " +
            "left join cocu_club club on a.club_id=club.id ${ew.customSqlSegment}")
    IPage<ActivityVO> listSmart(Page<ActivityVO> page, @Param(Constants.WRAPPER) Wrapper<Club> wrapper);

}
