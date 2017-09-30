package com.youjin.vaildator;

import com.youjin.annotation.Validator;
import com.youjin.util.WebUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rx on 17/2/13.
 */
public class ManagerValidator extends Validator {
    Map<String,Object> data=new HashMap<String,Object>();
    @Override
    protected boolean validate(HttpServletRequest request) {
        String uri=request.getRequestURI();
        String account=request.getParameter("username");
        String password=request.getParameter("password");
        if(uri.indexOf("/login")>=0){
            if(StringUtils.isBlank(account)){
                data= WebUtils.formatErrMsg(405,"用户名不能为空");
                return false;
            }
            if(StringUtils.isBlank(password)){
                data= WebUtils.formatErrMsg(405,"密码不能为空");
                return false;
            }
        }
        return true;
    }

    @Override
    protected Map<String, Object> handleError() {
        return data;
    }
}
