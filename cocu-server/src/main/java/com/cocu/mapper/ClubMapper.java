package com.cocu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cocu.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cocu.vo.ClubVO;
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
public interface ClubMapper extends BaseMapper<Club> {


    @Select("select cc.*, uc.email creator, up.email president, up.username presidentName, ct.name type from cocu_club cc\n" +
            "left join cocu_user uc on cc.creator_id = uc.id\n" +
            "left join cocu_user up on cc.president_id = up.id\n" +
            "left join cocu_club_type ct on cc.type_id = ct.id ${ew.customSqlSegment}")
    IPage<ClubVO> listSmart(Page<ClubVO> page, @Param(Constants.WRAPPER) Wrapper<Club> wrapper);


}
