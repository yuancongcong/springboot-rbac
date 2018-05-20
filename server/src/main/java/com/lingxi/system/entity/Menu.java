package com.lingxi.system.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.lingxi.framework.base.IdDateEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="sys_menu")
@DynamicUpdate
public class Menu extends IdDateEntity {

    /** 菜单名称 */
    private String name;
    /** 访问url地址 */
    private String url;
    /** 层级 */
    private Integer level;
    /** 叶子节点*/
    private Boolean isLeaf;
    /** 排序号 */
    private Integer sort;
    /** 是否可用 1可用 0不可用 */
    private Boolean disabled;
    /** 图标 */
    private String icon;

    /** 级联删除菜单配置 */
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    @JSONField(serialize = false)
    private List<Menu> children2;

    @Transient
    private List<Menu> children;

    private Long pid;

    private String pids;

    /** 用户角色 */
    @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
    @JSONField(serialize = false)
    private List<Role> roles =  new ArrayList<>();
}