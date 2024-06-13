package com.cocu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditVO {

    public String auditStatus;
    public String auditContent;

    public Long activityId;

    public boolean isRecommend;
}
