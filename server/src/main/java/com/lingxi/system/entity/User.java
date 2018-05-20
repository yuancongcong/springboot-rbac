package com.lingxi.system.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.lingxi.framework.base.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name="sys_user")
public class User extends IdEntity {

    /** 登录名 */
    private String userName;
    /** 真实姓名 */
    private String realName;
    /** 密码 */
    private String password;
    /** 账号是否锁定 1锁定 0不锁定 */
    private Boolean locked = false;
    /** 邮箱 */
    private String email;
    /** 联系方式 */
    private String tel;
    /** 性别 */
    private String sex;
    /** 地址 */
    private String address;
    /** 备注 */
    private String remark;
    /** 城市 */
    private String city;

    /** 扩展字段 */
    private String flag;
    /** 最后登陆时间 */

    @JSONField(serialize = false)
    private Date lastLoginTime;

    /** 用户角色 */
    @JSONField(serialize = false)
//    @ManyToMany(mappedBy = "users")
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    private List<Role> roles;

    @Transient
    @JSONField(name = "roles")
    private List<Role> rolesSerialize;

    public void setRolesSerialize(){
        this.rolesSerialize = this.roles;
    }
}