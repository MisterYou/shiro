package com.youjin.sql;

import com.youjin.domain.Manager;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by train on 17/2/16.
 */
public class ManagerSql {
    public String update(final Manager manager){
        return new SQL(){
            {
                UPDATE("sys_manager");

                //通过条件 判断是否需要更新该字段

                if (StringUtils.isNotBlank(manager.getName())) {
                    SET("name = #{name}");
                }

                if (StringUtils.isNotBlank(manager.getPassword())) {
                    SET("password = #{password}");
                }

                WHERE("id = #{id}");
            }

        }.toString();
    }

}
