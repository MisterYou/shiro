package com.youjin.dao;

import com.youjin.domain.Module;
import com.youjin.sql.ModuleSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by rx on 17/2/13.
 */
public interface ModuleDAO {

    /**
     * 新增
     * @param module
     * @throws Exception
     */
    @Insert("insert into sys_module(name,path,type,module_key,created_at) values(#{name},#{path},#{type},#{moduleKey},#{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Module module) throws Exception;

    /**
     * 分页查询
     * @return
     */
    @Select("select * from sys_module order by id desc")
    List<Module> findAll();

    /**
     * 根据角色查询相应的模块
     * @param roleId
     * @return
     */
    @Select("select a.* from sys_module a,sys_role_module b where a.id=b.module_id and b.role_id=#{roleId}")
    List<Module> findByRole(int roleId);
    /**
     * 更新
     * @param module
     * @throws Exception
     */
    @UpdateProvider(type= ModuleSql.class,method = "update")
    int update(Module module) throws Exception;

    /**
     * 删除
     * @param moduleId
     * @return
     * @throws Exception
     */
    @Delete("delete from sys_module where id=#{moduleId}")
    int delete(int moduleId) throws Exception;


    /**
     * 根据权限id删除对应的权限关系
     * @param moduleId
     * @return
     * @throws Exception
     */
    @Delete("delete from sys_role_module where module_id=#{moduleId}")
    int deleteModuleRelation(int moduleId) throws Exception;
    /**
     * 获取该人的权限模块
     * @param managerId
     * @return
     */
    @Select(value="select distinct(module_key),e.* from sys_manager_role b " +
            "left join sys_role c on b.role_id=c.id " +
            "left join sys_role_module d on c.id=d.role_id " +
            "left join sys_module e on d.module_id=e.id " +
            "where b.manager_id=#{managerId}")
    List<Module> findModuleByUserId(int managerId);

    /**
     * 根据path获取module
     * @param path
     * @return
     */
    @Select("select * from sys_module where path=#{path}")
    Module findByPath(String path);

    /**
     * 根据角色获取对应的权限
     * @param roleIds
     * @return
     */
    @Select("select * from sys_module where role_id in(#{roleIds})")
    List<Module> findModuleByRoles(String roleIds);
}
