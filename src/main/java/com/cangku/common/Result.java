package com.cangku.common;

import lombok.Data;
import org.omg.CORBA.ObjectHelper;
@Data
public class Result {

    private int code;//200/400
    private String msg;//成功/失败
    private Long total;//记录数
    private Object data;//数据

    public static Result fails(){
        return result(400,"失败",0L,null);
    }
    public static Result suc(){
        return result(200,"成功",0L,null);
    }
    public static Result suc(Object data){
        return result(200,"成功",0L,data);
    }
    public static Result suc(Object data,Long total){
        return result(200,"成功",total,data);
    }

    private static Result result(int code,String msg,Long total,Object data){
        Result res=new Result();
         res.setCode(code);
         res.setMsg(msg);
         res.setTotal(total);
         res.setData(data);
        return res;
    }
}
