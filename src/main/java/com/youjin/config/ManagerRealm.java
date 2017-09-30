package com.youjin.config;

import com.youjin.common.Const;
import com.youjin.domain.Manager;
import com.youjin.service.ManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证用户登录
 *
 * @author Administrator
 */
@Component("managerRealm")
public class ManagerRealm extends AuthorizingRealm {
    @Autowired
    private ManagerService managerService;

    public ManagerRealm() {
        setName("ManagerRealm");
        // 采用MD5加密
        setCredentialsMatcher(new HashedCredentialsMatcher("md5"));
    }

    //权限资源角色
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //add Permission Resources
        info.setStringPermissions(managerService.findPermissions(username));
        //add Roles String[Set<String> roles]
        //info.setRoles(roles);
        return info;
    }

    //登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String userName = upt.getUsername();
        Manager manager = managerService.findByAccount(userName);

        if (manager == null) {
            throw new UnknownAccountException();
        }
        //加密盐
        //加密盐放在密码的前面
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName+ Const.PASSWORD_SALT_PART);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName,manager.getPassword(),credentialsSalt,getName());

        return info;
    }
}