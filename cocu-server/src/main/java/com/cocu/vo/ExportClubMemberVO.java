package com.cocu.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * export excel, no need pagination
 */
@Data
public class ExportClubMemberVO {
    @Excel(name="Username", orderNum="1", width=30)
    private String username;

    @Excel(name="Gender", orderNum="1", width=30, replace={"Male_1","Female_0"})
    private String gender;
    @Excel(name="Mobile", orderNum="1", width=50)
    private String mobile;
}
