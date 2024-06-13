package com.cocu.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.cocu.entity.ClubMember;
import com.cocu.service.ClubMemberService;
import com.cocu.vo.ExportClubMemberVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ryanangry
 * @since 2024-01-08
 */
@RestController
@RequestMapping("/club-member")
public class ClubMemberController {

    @Resource
    private ClubMemberService clubMemberService;

    // export club member excel
    @RequestMapping("/exportClubMemberExcel")
    public void exportClubMemberExcel(HttpServletResponse response, Long clubId) throws Exception{
        // excel data type list
        List<ExportClubMemberVO> memberList = new ArrayList<>();
        // retrieve data from database
        List<ClubMember> members = clubMemberService.exportClubMemberExcel(clubId);
        // assign to excel data type list
        for(ClubMember clubMember : members){
            ExportClubMemberVO clubMemberVO = new ExportClubMemberVO();
            BeanUtils.copyProperties(clubMember, clubMemberVO);
            memberList.add(clubMemberVO);
        }
        // export
        String fileName = "clubMember.xlsx";
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExportClubMemberVO.class, memberList);
        downloadExcel(fileName, workbook, response);
    }

    public static void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response) throws Exception{
        try{
            if(StringUtils.isEmpty(fileName)){
                throw new RuntimeException("Export file name cannot be null");
            }
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Type", "application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
            response.setHeader("FileName", encodeFileName);
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            workbook.write(response.getOutputStream());
            workbook.close();
        }catch (Exception e){
            workbook.close();
            e.printStackTrace();
        }
    }

}
