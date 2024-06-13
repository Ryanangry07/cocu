package com.cocu.controller;

import com.cocu.service.UserApplyClubService;
import com.cocu.vo.AuditVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserApplyClubControllerTest {

    @Autowired
    public UserApplyClubService userApplyClubService;

    @Test
    public void testAudit(){
        AuditVO auditVO = new AuditVO();
        userApplyClubService.audit(auditVO);
    }


    @Test
    public void testAudit1(){
        AuditVO auditVO = new AuditVO();
        auditVO.setAuditContent("1231412");
        userApplyClubService.audit(auditVO);
    }


    @Test
    public void testAudit2(){
        AuditVO auditVO = new AuditVO();
        auditVO.setAuditStatus("1");
        auditVO.setAuditContent("21351231");
        auditVO.setActivityId(12L);
        userApplyClubService.audit(auditVO);
    }


    @Test
    public void testAudit3(){
        AuditVO auditVO = new AuditVO();
        userApplyClubService.audit(auditVO);
    }


    @Test
    public void testAudit4(){
        AuditVO auditVO = new AuditVO();
        userApplyClubService.audit(auditVO);
    }

}
