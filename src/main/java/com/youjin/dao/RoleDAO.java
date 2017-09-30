package com.youjin.dao;

import com.youjin.domain.Role;
import com.youjin.sql.RoleSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by train on 2017/2/14.
 */
public interface RoleDAO {

    /**
     * 新增
     * @param role
     * @throws Exception
     */
    @Insert("insert into sys_role(name,description,created_at) values(#{name},#{description},#{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Role role) throws Exception;

    /**
     * 分页查询
     * @return
     */
    @Select("select *,id as roleId from sys_role order by id desc")
    @Results(value = {
            @Result(property="modules", javaType=List.class, column="roleId",
                    many=@Many(select="com.rx.dao.ModuleDAO.findByRole"))
    })
    List<Role> findAll();

    @Select("select a.* from sys_role a,sys_manager_role b where a.id=b.role_id and b.manager_id=#{managerId}")
    List<Role> findByManager(int managerId);
    /**
     * 更新
     * @param role
     * @throws Exception
     */
    @UpdateProvider(type= RoleSql.class,method = "update")
    int update(Role role) throws Exception;

    /**
     * 删除
     * @param roleId
     * @return
     * @throws Exception
     */
    @Delete("delete from sys_role where id=#{roleId}")
    int delete(int roleId) throws Exception;

    /**
     * 根据角色删除对应的权限关系
     * @param roleId
     * @return
     * @throws Exception
     */
    @Delete("delete from sys_role_module where role_id=#{roleId}")
    int deleteRoleRelation(int roleId) throws Exception;


    /**
     * 根据用户名获取角色
     * @param account
     * @return
     */
    @Select("select * from sys_role a " +
            "left join sys_manager_role b on a.id=b.role_id " +
            "left join sys_manager c on c.id=b.manager_id " +
            "where c.account=#{account} limit 1")
    Role findByAccount(String account);

    /**
     * 授权
     * @param roleId
     * @param permissions
     * @return
     */
    @InsertProvider(type= RoleSql.class,method = "setupPermissions")
    int setupPermissions(int roleId, String permissions) throws Exception;
}
