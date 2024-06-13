package com.cocu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {


    private int code;       //code 200/400
    private String msg;     //message "success"
    private Long total;     //total number of records
    private Object data;    //data

    public static Result fail(){
        return result(400, "failed", 0L, null);
    }

    public static Result fail(String msg){
        return result(400, msg, 0L, null);
    }

    public static Result success(){
        return result(200, "succeed", 0L, null);
    }

    public static Result success(String msg){
        return result(200, msg, 0L, null);
    }

    public static Result success(Object data, String msg){
        return result(200, msg, 0L, data);
    }

    public static Result success(Object data, Long total){
        return result(200, "成功", total, data);
    }

    private static Result result(int code, String msg, Long total, Object data){
        return new Result(code, msg, total, data);
    }
}
