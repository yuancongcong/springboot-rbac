package com.lingxi.framework.domain.jpa;

import com.lingxi.framework.utils.EmptyUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Criteria的简易封装
 * 1、空值不进行查询条件
 * 2、支持属性.查询 默认LEFT JOIN
 * 3、支持链式写法
 */
public class QlQuery implements MyQuery {

    @Getter
    private List<Object> params = new ArrayList<>();

    @Getter
    private StringBuilder qlString = new StringBuilder();

    @Getter
    private boolean nativeQuery;

    public QlQuery() {
    }

    public QlQuery(boolean nativeQuery) {
        this.nativeQuery = nativeQuery;
    }

    public QlQuery addQl(String sql){
        qlString.append(sql);
        return this;
    }

    public QlQuery addQl(String sql, Object value){
        if(this.isEmpty(value)) return this;
        this.addQl(sql);
        params.add(value);
        return this;
    }


    private boolean isEmpty(Object value) {
        return EmptyUtil.isEmpty(value);
    }
}
