package com.lingxi.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.lingxi.framework.base.IdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="sys_dictionary")
public class Dictionary extends IdEntity {

    private String name;
    private String code;
    private Boolean isLeaf;
    private Integer level;
    private String flag;
    private Long sort;

    private Long pid;

    /** 级联删除菜单配置 */
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    @JSONField(serialize = false)
    private List<Dictionary> children2;

    @Transient
    private List<Dictionary> children;
}
