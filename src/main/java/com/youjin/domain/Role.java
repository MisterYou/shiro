package com.youjin.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by train on 2017/2/14.
 */

public class Role extends BaseDomain {

    //角色下面关联了系统用户
    public static int HAS_MANAGERS=0X1100;

    private int id;
    private String name;
    private String description;
    private Date createdAt;
    private List<Module> modules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
