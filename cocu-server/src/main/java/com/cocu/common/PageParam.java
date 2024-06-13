package com.cocu.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class PageParam {

    /*
    {
        "pageSize":20,      // para.getPageSize()
        "pageNum":1,        // para.getPageNum()
        "data":{            // para.getData()
            "no":"admin"    // (String)data.get("no")
        }
    }
     */

    private final static int PAGE_SIZE = 20;
    private final static int PAGE_NUM = 1;

    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;
    private HashMap data = new HashMap();
}