package com.youjin.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rx on 17/2/13.
 */
public class WebUtils {

    public WebUtils() {

    }

    public static boolean isAjax(HttpServletRequest request){
        if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
            return true;
        }
        return false;

    }

    public static void writeJson(HttpServletResponse response,Object obj){
        /* 设置格式为text/json    */
        response.setContentType("text/json");
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(obj));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String,Object> formatErrMsg(int errcode, String errmsg) {
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("errcode", errcode);
        data.put("errmsg", errmsg);
        return data;
    }
    public static Map<String,Object> dbErrMsg() {
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("errcode", -1);
        data.put("errmsg", "操作失败");
        return data;
    }


}
