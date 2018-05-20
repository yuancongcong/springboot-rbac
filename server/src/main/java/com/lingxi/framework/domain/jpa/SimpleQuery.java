package com.lingxi.framework.domain.jpa;

import com.lingxi.framework.utils.EmptyUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * JPA Criteria的简易封装
 * 1、空值不进行查询条件
 * 2、支持属性.查询 默认LEFT JOIN
 * 3、支持链式写法
 */
public class SimpleQuery implements MyQuery {

    @Getter
    private List<Predicate> predicates;

    private CriteriaBuilder cb;

    /** 查询条件列表 */
    @Setter
    private Root from;

    public SimpleQuery(CriteriaBuilder cb, Root<?> root) {
        this.cb = cb;
        this.from = root;
        predicates = new ArrayList<>();
    }

    public SimpleQuery add(Predicate ...predicate){
        return add(Arrays.asList(predicate));
    }
    public SimpleQuery add(Collection<Predicate>predicates){
        predicates.addAll(predicates);
        return this;
    }

    public SimpleQuery eq(String propertyName, Object value){
        if(isEmpty(value)) return this;
        predicates.add(cb.equal(getPath(propertyName),value));
        return this;
    }

    public SimpleQuery isNotNull(String propertyName){
        predicates.add(cb.isNotNull(getPath(propertyName)));
        return this;
    }

    public SimpleQuery isNull(String propertyName){
        predicates.add(cb.isNull(getPath(propertyName)));
        return this;
    }

    public SimpleQuery eqOrIsNull(String propertyName, Object value){
         if(value == null) isNull(propertyName);
         else eq(propertyName,value);
        return this;
    }

    public SimpleQuery llike(String propertyName, String value){
        if(isEmpty(value)) return this;
        if (value.indexOf("%") < 0)
            value = value + "%";
        predicates.add(cb.like(getPath(propertyName),value));
        return this;
    }
    public SimpleQuery rlike(String propertyName, String value){
        if(isEmpty(value)) return this;
        if (value.indexOf("%") < 0)
            value = "%" + value ;
        predicates.add(cb.like(getPath(propertyName),value));
        return this;
    }

    public SimpleQuery like(String propertyName, String value){
        if(isEmpty(value)) return this;
        if (value.indexOf("%") < 0)
            value = "%" + value + "%";
        predicates.add(cb.like(getPath(propertyName),value));
        return this;
    }

    public SimpleQuery or(List<String> propertyName, String value){
        if(isEmpty(value) || isEmpty(propertyName)) return this;
        Predicate predicate = cb.or(cb.equal(from.get(propertyName.get(0)), value));
        for (int i = 1; i < propertyName.size(); ++i)
            predicate = cb.or(predicate, cb.equal(from.get(propertyName.get(i)), value));
        this.predicates.add(predicate);
        return this;
    }

    public SimpleQuery or(String[] propertyName, String value){
         return or(Arrays.asList(propertyName),value);
    }

    public SimpleQuery or(Predicate... predicates){
        if(isEmpty(predicates)) return this;
        Predicate predicate = cb.and(cb.or(predicates));
        this.predicates.add(predicate);
        return this;
    }

    /**
     * 时间区间查询
     *
     * @param propertyName
     *            属性名称
     * @param lo
     *            属性起始值
     * @param go
     *            属性结束值
     */
    public SimpleQuery between(String propertyName, Date lo, Date go) {
        if (isEmpty(lo) || isEmpty(go)) return this;
        this.predicates.add(cb.between(getPath(propertyName), lo, go));
        return this;
    }


    /**
     * in
     *
     * @param propertyName
     *            属性名称
     * @param value
     *            值集合
     */
    public SimpleQuery in(String propertyName, Collection value) {
        if(isEmpty(value)) return this;
        Iterator iterator = value.iterator();
        CriteriaBuilder.In in = cb.in(getPath(propertyName));
        while (iterator.hasNext()) {
            in.value(iterator.next());
        }
        this.predicates.add(in);
        return this;
    }

    /**
     * in
     *
     * @param path
     *            属性ROOT
     * @param value
     *            值集合
     */
    public SimpleQuery in(Path path, Collection value) {
        if(isEmpty(value)) return this;
        Iterator iterator = value.iterator();
        CriteriaBuilder.In in = cb.in(path);
        while (iterator.hasNext()) {
            in.value(iterator.next());
        }
        this.predicates.add(in);
        return this;
    }

    public Path join(String attributeName ,JoinType joinType){

        String[] attrs = attributeName.split("[.]");
        Join join = this.from.join(attrs[0],joinType);
        Path path = null;
        for(int i=1;i< attrs.length;i++){
            if(path == null){
                path = join.get(attrs[i]);
            }else{
                path = path.get(attrs[i]);
            }
        }
        return path;
    }

    private Path getPath(String attributeName,JoinType joinType) {
        String[] attrs = attributeName.split("[.]");
        if (attrs.length > 1) {
            Join join = this.from.join(attrs[0], joinType);
            Path path = null;
            for (int i = 1; i < attrs.length; i++) {
                if (path == null) {
                    path = join.get(attrs[i]);
                } else {
                    path.get(attrs[i]);
                }
            }
            return path;
        } else {
            return from.get(attributeName);
        }
    }

    private Path getPath(String attributeName){
        return getPath(attributeName,JoinType.LEFT);
    }


    public Predicate toPredicate(){
        return toPredicate(false);
    }

    public Predicate toPredicate(boolean isOr){
        if(isEmpty(this.predicates)) return null;
        if(isOr){
            return cb.or(this.predicates.toArray(new Predicate[this.predicates.size()]));
        }
        return cb.and(this.predicates.toArray(new Predicate[this.predicates.size()]));
    }

    public Predicate[] toPredicates(){
        if(isEmpty(this.predicates)) return null;
        return this.predicates.toArray(new Predicate[this.predicates.size()]);
    }

    private boolean isEmpty(Object value) {
        return EmptyUtil.isEmpty(value);
    }
}
