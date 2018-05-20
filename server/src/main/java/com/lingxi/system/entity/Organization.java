package com.lingxi.system.entity;

import com.lingxi.framework.base.IdEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="sys_organization")
public class Organization extends IdEntity {

    private String name;
}
