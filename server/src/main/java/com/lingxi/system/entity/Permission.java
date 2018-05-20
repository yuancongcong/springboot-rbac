package com.lingxi.system.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.lingxi.framework.base.IdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="sys_permission")
public class Permission extends IdEntity {

    /** 资源名称 */
    private String name;
    /** 资源类型：menu,button */
    private String type;
    /** 访问url地址 */
    private String url;
    /** 备注 */
    private String remark;
    /** 是否可用 1可用 0不可用 */
    private Boolean disabled;

    /** 用户角色 */
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = @JoinColumn(name = "permission_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JSONField(serialize = false)
    private List<Role> roles;

    @Transient
    @JSONField(name = "roles")
    private List<Role> rolesSerialize;

    public void setRolesSerialize(){
        this.rolesSerialize = this.roles;
    }
}