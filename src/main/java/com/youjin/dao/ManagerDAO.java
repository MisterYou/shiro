package com.youjin.dao;

import com.youjin.domain.Manager;
import com.youjin.sql.ManagerSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by rx on 2017/1/25.
 */

public interface ManagerDAO {

    /**
     * 新增
     * @param manager
     * @throws Exception
     */
    @Insert("insert into sys_manager(account,password,name,created_at) values(#{account},#{password},#{name},#{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Manager manager) throws Exception;

    /**
     * 更新
     * @param manager
     * @throws Exception
     */
    @UpdateProvider(type= ManagerSql.class,method = "update")
    int update(Manager manager) throws Exception;

    @Update("update sys_manager_role set role_id=#{roleId} where manager_id=#{managerId}")
    int updateRole(@Param("managerId") int managerId, @Param("roleId") int roleId) throws Exception;

    @Insert("insert into sys_manager_role(role_id,manager_id) values(#{roleId},#{managerId})")
    void setupRole(@Param("managerId") int managerId, @Param("roleId") int roleId) throws Exception;

    /**
     * 删除
     * @param managerId
     * @return
     * @throws Exception
     */
    @Delete("delete from sys_manager where id=#{managerId}")
    int delete(int managerId) throws Exception;

    @Delete("delete from sys_manager_role where manager_id=#{managerId}")
    int deleteRole(int managerId) throws Exception;

    @Select("select *from sys_manager where account=#{account}")
    Manager findByAccount(String account);

    @Select("select *,id as managerId from sys_manager where account<>'admin' order by id desc")
    @Results(value = {
            @Result(property="roles", javaType=List.class, column="managerId",
                    many=@Many(select="com.rx.dao.RoleDAO.findByManager"))
    })
    List<Manager> findAll();

    @Select("select a.* from sys_manager a,sys_manager_role b where a.id=b.manager_id and b.role_id=#{roleId}")
    List<Manager> findByRole(int roleId);


}
