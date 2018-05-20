package com.lingxi.framework.base.impl;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.ReflectHelper;
import com.lingxi.framework.domain.jpa.MyQuery;
import com.lingxi.framework.domain.jpa.QlQuery;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.system.entity.Dictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 自定义repository的方法接口实现类,该类主要提供自定义的公用方法 
 *  
 * @author yuancongcong
 * @param <T>
 * @param <ID> 
 */
@Slf4j
@NoRepositoryBean
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {
  
    /**
     * 持久化上下文 
     */  
    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    @Transactional
    public int executeUpdate(String qlString, Object... values) {  
        return createQuery(qlString, Arrays.asList(values),false).executeUpdate();
    }
    @Override
    @Transactional
    public int executeUpdate(String qlString, List<Object> values) {
        return createQuery(qlString,values,false).executeUpdate();
    }
  
    @Override
    @Transactional
    public int executeUpdate(String qlString, Map<String, Object> params) {
        Query query = entityManager.createQuery(qlString);  
        for (String name : params.keySet()) {  
            query.setParameter(name, params.get(name));  
        }  
        return query.executeUpdate();
    }  

    @Transactional
    public long deleteByIds(ID[] ids){

        long count =  0l;
        for(ID id : ids){
            this.delete(id);
            count++;
        }
        return count;
    }


    /**
     * 空属性不做修改
     * @param entity
     * @return
     */
    @Transactional
    public T saveSelective(T entity){
        if(entity==null)return null;

        try{
            Class<?> clazz = entity.getClass();
            Field idField = ReflectHelper.getIdField(clazz);
            T newEntity = entity;
            if(idField!=null){
                ID val = (ID)idField.get(entity);
                if(val != null){
                    newEntity = this.findOne(val);
                    BeanUtils.copyProperties(entity,newEntity);
                }
            }
            return this.save(newEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> findAll(String qlString,boolean nativeQuery) {
        return createQuery(qlString,null,nativeQuery).getResultList();
    }
    @Override
    public List<T> findAll(String qlString) {
        return createQuery(qlString,null,false).getResultList();
    }

    public List<T> findAll(String qlString, List<Object> values,boolean nativeQuery){
        return createQuery(qlString,values,nativeQuery).getResultList();
    }

    @Override
    public Page<T> findAll(String qlString, List<Object> values,Pageable pageable,boolean nativeQuery){
        List<T> contents = createQuery(qlString,values,nativeQuery)
                .setFirstResult(pageable.getPageNumber() *  pageable.getPageSize())
                .setMaxResults((pageable.getPageNumber()+1) *  pageable.getPageSize())
                .getResultList();

        String countQlString = "select count(*) " + qlString.substring(qlString.indexOf("from"));
        long total = (Long)createQuery(countQlString,values,nativeQuery).getSingleResult();
        return  new PageImpl(contents,pageable,total);
    }

    private Query createQuery(String qlString,List<Object> values,boolean nativeQuery){
        Query query = nativeQuery ? entityManager.createNativeQuery(qlString): entityManager.createQuery(qlString);
        if(values!= null){
            Iterator<Object> iterator = values.iterator();
            for (int i=0;iterator.hasNext();i++) {
                query.setParameter(i + 1, iterator.next());
            }
        }
        return query;
    }

    @Override
    public List<T> findAll(MyQuery query) {
        if(query instanceof QlQuery){
            QlQuery qlQuery = ((QlQuery) query);
            return this.findAll(qlQuery.getQlString().toString(),qlQuery.getParams(),qlQuery.isNativeQuery());
        }
        if(query instanceof SimpleQuery){
            return this.findAll((Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                    ((SimpleQuery) query).toPredicate());
        }
        return null;
    }

    @Override
    public Page<T> findAll(MyQuery query, Pageable pageable) {
        if(query instanceof QlQuery){
            QlQuery qlQuery = ((QlQuery) query);
            return this.findAll(qlQuery.getQlString().toString(),qlQuery.getParams(),pageable,qlQuery.isNativeQuery());
        }
        if(query instanceof SimpleQuery){
            return this.findAll((Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                    ((SimpleQuery) query).toPredicate(),pageable);
        }
        return null;
    }

}