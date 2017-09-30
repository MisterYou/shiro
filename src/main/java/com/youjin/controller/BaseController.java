package com.youjin.controller;

import com.github.pagehelper.Page;
import com.youjin.common.Const;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by train on 17/2/16.
 */
public class BaseController {

    public Page getPage(HttpServletRequest request){
        int pageNum=1;
        int pageSize=Const.ROWS_PER_PAGE;
        if(StringUtils.isNumeric(request.getParameter("page"))){
            pageNum=Integer.parseInt(request.getParameter("page").toString());
        }
        if(StringUtils.isNumeric(request.getParameter("pageSize"))){
            pageSize=Integer.parseInt(request.getParameter("pageSize").toString());
        }
        return new Page(pageNum,pageSize);
    }
}
