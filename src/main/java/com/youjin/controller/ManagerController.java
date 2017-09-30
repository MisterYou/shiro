package com.youjin.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.youjin.annotation.Before;
import com.youjin.domain.Manager;
import com.youjin.service.ManagerService;
import com.youjin.util.EncryptUtils;
import com.youjin.vaildator.ManagerValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 系统管理员
 * Created by youjin on 2017/8/17.
 */

@RestController
@RequestMapping("manager")
public class ManagerController extends BaseController{

    @Autowired
    ManagerService managerService;

    @RequestMapping("info")
    public Object info(HttpServletRequest request){

        Subject subject = SecurityUtils.getSubject();

        if(null==subject || subject.getPrincipal()==null){
            return Maps.newHashMap();
        }

        return managerService.findByAccount(subject.getPrincipal().toString());
    }


    @RequestMapping("login")
    @Before(ManagerValidator.class)
    public Object login(HttpServletRequest request){

        String account=request.getParameter("username").toString();
        String password=request.getParameter("password").toString();
//        String pas = EncryptUtils.md5("admin"+ "youjin"+"123456");
//        System.out.println("password"+pas);

        UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(upt);
            //获取用户信息

            Manager manager=managerService.findByAccount(subject.getPrincipal().toString());
            if(manager.getKey()!=null&&!manager.getKey().isEmpty()){
                //获取用户的权限列表
                Set<String> permissions= managerService.findPermissions(account);
                return ImmutableMap.of("permissions",permissions,"name",manager.getName());
            }
            return ImmutableMap.of("name",manager.getName());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            //登录失败
            return ImmutableMap.of("errcode",1,"errmsg","用户名或密码错误");
        }

    }
}
