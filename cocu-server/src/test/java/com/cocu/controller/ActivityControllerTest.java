package com.cocu.controller;

import com.cocu.service.ActivityService;
import com.cocu.vo.AuditVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ActivityControllerTest {

    @Autowired
    public ActivityService activityService;

    @Test
    public void testAudit(){
        AuditVO auditVO = new AuditVO();
        auditVO.setActivityId(1L);
        auditVO.setAuditStatus("1");
        auditVO.setAuditContent("Good!!");
        activityService.audit(auditVO);
    }


    @Test
    public void testAudit2(){
        AuditVO auditVO = new AuditVO();
        auditVO.setActivityId(1L);
        auditVO.setAuditStatus("1");
        activityService.audit(auditVO);
    }


    @Test
    public void testAudit3(){
        AuditVO auditVO = new AuditVO();
        auditVO.setActivityId(1L);
        auditVO.setAuditContent("Good!!");
        activityService.audit(auditVO);
    }

    @Test
    public void testAudit4(){
        AuditVO auditVO = new AuditVO();
        activityService.audit(auditVO);
    }
}
