package com.lingxi.framework.base;

import com.lingxi.framework.domain.jpa.MyQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 自定义Repository的方法接口 
 * @author yuancongcong
 * @param <T> 对象即实体类
 * @param <ID>对象的注解
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

    /** 
     * 执行ql语句 
     * @param qlString 基于jpa标准的ql语句 
     * @param values ql中的?参数值,单个参数值或者多个参数值 
     * @return 返回执行后受影响的数据个数 
     */  
    int executeUpdate(String qlString, Object... values);  
  
    /** 
     * 执行ql语句 
     * @param qlString 基于jpa标准的ql语句 
     * @param params key表示ql中参数变量名，value表示该参数变量值 
     * @return 返回执行后受影响的数据个数 
     */  
    int executeUpdate(String qlString, Map<String, Object> params);
      
    /** 
     * 执行ql语句，可以是更新或者删除操作 
     * @param qlString 基于jpa标准的ql语句 
     * @param values ql中的?参数值 
     * @return 返回执行后受影响的数据个数 
     * @throws Exception 
     */  
    int executeUpdate(String qlString, List<Object> values);

    /**
     * 执行ql语句，可以是更新或者删除操作
     * @param qlString 基于jpa标准的ql语句
     * @param values ql中的?参数值
     * @return 返回执行后受影响的数据个数
     * @throws Exception
     */
    List<T> findAll(String qlString, boolean isNative);
    List<T> findAll(String qlString, List<Object> values,boolean isNative);
    Page<T> findAll(String qlString, List<Object> values,Pageable pageable,boolean isNative);
    List<T> findAll(String qlString);

    List<T> findAll(MyQuery query);
    Page<T> findAll(MyQuery query, Pageable pageable);


    /**
     * 多id删除
     * @param ids
     * @return 返回成功个数
     */
    long deleteByIds(ID[] ids);


    /**
     * 空属性不做修改
     * @param entity
     * @return
     */
    T saveSelective(T entity);

} 