package com.lingxi.framework.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class IdEntity2  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

}
