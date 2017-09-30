package com.youjin.domain;
import java.io.Serializable;
import java.util.Date;

public class Module implements Serializable{

    private static final long serialVersionUID = -4297656027873404254L;
    /**路径*/
    public static final int URL_TYPE=1;
    /**功能点*/
    public static final int FUNCTION_TYPE=2;

    private int id;
    private String name;
    private String path;
    private int type;
    private String moduleKey;
    private Date createdAt;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}