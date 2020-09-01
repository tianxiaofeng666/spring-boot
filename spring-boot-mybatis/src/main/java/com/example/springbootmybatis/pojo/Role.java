package com.example.springbootmybatis.pojo;

import java.util.Date;

public class Role {
    private int id;
    private String name;
    private String isAssign;
    private String isDeleted;
    private String gmtCreated;
    private Long creator;
    private String gmtModified;
    private Long modifier;

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

    public String getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(String isAssign) {
        this.isAssign = isAssign;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Role(){

    }

    public Role(int id, String name, String isAssign, String isDeleted, String gmtCreated, Long creator, String gmtModified, Long modifier) {
        this.id = id;
        this.name = name;
        this.isAssign = isAssign;
        this.isDeleted = isDeleted;
        this.gmtCreated = gmtCreated;
        this.creator = creator;
        this.gmtModified = gmtModified;
        this.modifier = modifier;
    }
}
