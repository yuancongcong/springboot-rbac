package com.lingxi.system.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.lingxi.framework.base.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name="sys_role")
public class Role extends IdEntity {

    /** 名称 */
    private String name;
    /** 代码 */
    private String code;
    /** 是否可用 1可用 0不可用 */
    private Boolean disabled;

    @ManyToMany(targetEntity = Menu.class, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    @JSONField(serialize = false)
    private List<Menu> menus;

    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<User> users;
}