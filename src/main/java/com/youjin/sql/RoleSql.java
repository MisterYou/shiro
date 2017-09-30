package com.youjin.sql;

import com.youjin.domain.Role;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by train on 17/2/16.
 */
public class RoleSql {
    public String update(final Role role){
        return new SQL(){
            {
                UPDATE("sys_role");

                //通过条件 判断是否需要更新该字段
                if (StringUtils.isNotBlank(role.getName())) {
                    SET("name = #{name}");
                }

                if (StringUtils.isNotBlank(role.getDescription())) {
                    SET("description = #{description}");
                }

                WHERE("id = #{id}");
            }

        }.toString();
    }
    public String setupPermissions(final int roleId,final String permissions){
        StringBuilder sql = new StringBuilder("insert into sys_role_module(role_id,module_id) values");
        String p[]=permissions.split(",");
        for (int i=0;i<p.length;i++){
            sql.append("(");
            sql.append(roleId);
            sql.append(",");
            sql.append(p[i]);
            sql.append("),");
        }
        //删掉最后一个多余的逗号
        sql.deleteCharAt(sql.length()-1);
        return sql.toString();
    }
}
