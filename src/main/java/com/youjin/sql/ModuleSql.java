package com.youjin.sql;

import com.youjin.domain.Module;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by train on 17/2/16.
 */
public class ModuleSql {
    public String update(final Module module){
        return new SQL(){
            {
                UPDATE("sys_module");

                //通过条件 判断是否需要更新该字段
                if (StringUtils.isNotBlank(module.getName())) {
                    SET("name = #{name}");
                }

                if (StringUtils.isNotBlank(module.getPath())) {
                    SET("path = #{path}");
                }
                if (StringUtils.isNotBlank(module.getModuleKey())) {
                    SET("module_key = #{moduleKey}");
                }

                SET("type = #{type}");
                WHERE("id = #{id}");
            }

        }.toString();
    }
}
