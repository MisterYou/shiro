package com.youjin.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.youjin.common.Const;
import com.youjin.dao.ManagerDAO;
import com.youjin.dao.ModuleDAO;
import com.youjin.dao.RoleDAO;
import com.youjin.domain.Manager;
import com.youjin.domain.Module;
import com.youjin.util.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by rx on 2017/1/25.
 */
@Service
public class ManagerService {
    @Autowired
    private ManagerDAO managerDAO;
    @Autowired
    private ModuleDAO moduleDAO;
    @Autowired
    private RoleDAO roleDAO;

    public boolean store(Manager manager){
        manager.setCreatedAt(new Date());
        manager.setPassword(EncryptUtils.md5(manager.getAccount()+ Const.PASSWORD_SALT_PART+manager.getPassword()));
        try {
            return managerDAO.insert(manager)>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    @Transactional
    public void update(Manager manager,int roleId) throws Exception {
        if(!StringUtils.isBlank(manager.getPassword())){
            //修改密码
            manager.setPassword(EncryptUtils.md5(manager.getAccount()+ Const.PASSWORD_SALT_PART+manager.getPassword()));
        }
        managerDAO.update(manager);
        //检查用户是否有角色
        if(null==roleDAO.findByAccount(manager.getAccount())){
            //新增角色
            managerDAO.setupRole(manager.getId(),roleId);
        }else{
            //修改角色
            managerDAO.updateRole(manager.getId(),roleId);
        }

    }
    @Transactional
    public void delete(int managerId) throws Exception {
        managerDAO.delete(managerId);
        //删除角色关系
        managerDAO.deleteRole(managerId);
    }

    public Object pagination(Page page){
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Manager> managers=managerDAO.findAll();
        Map pageData= ImmutableMap.of("rows",((Page) managers).getTotal(),"total",((Page) managers).getPages(),"current",page.getPageNum());
        return ImmutableMap.of("page",pageData,"data",managers);
    }

    /**
     * 根据账号Account查询当前用户
     * @param account
     * @return
     */
    public Manager findByAccount(String account) {
        return managerDAO.findByAccount(account);
    }

    /**
     * 获取资源集合
     * @param account
     * @return
     */
    public Set<String> findPermissions(String account) {
        Set<String> set = Sets.newHashSet();
        Manager user = findByAccount(account);

        List<Module> modules = moduleDAO.findModuleByUserId(user.getId());

        for(Module module: modules) {
            if(null!=module){
                set.add(module.getModuleKey());
            }

        }
        return set;
    }

    /**
     * 获取权限列表
     * @param account
     * @return
     */
    public List<Module> findPermissionsWithArray(String account) {
        Set<String> set = Sets.newHashSet();
        Manager user = findByAccount(account);
        return moduleDAO.findModuleByUserId(user.getId());
    }

    /**
     * 获取URL权限
     * @param account
     * @return
     */
    public List<String> findPermissionUrl(String account) {
        List<String> list = Lists.newArrayList();
        Manager user = findByAccount(account);
        List<Module> modules = moduleDAO.findModuleByUserId(user.getId());

        for(Module module: modules) {
            if(null!=module&&module.getType() == Module.URL_TYPE) {
                list.add(module.getPath());
            }
        }
        return list;
    }
}
