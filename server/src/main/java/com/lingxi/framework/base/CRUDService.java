package com.lingxi.framework.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class CRUDService<T,ID extends Serializable> {

    private BaseRepository<T,ID> baseDao;

    public abstract BaseRepository<T, ID> getBaseDao();

    /**

     * 保存
     */
    public T save(T entity){
        return getBaseDao().saveSelective(entity);
    }

    /**
     * 删除
     */
    public void delete(ID id){
        getBaseDao().delete(id);
    }

    /**
     * 删除
     * @param ids
     */
    public void deleteByIds(ID ...ids){
        getBaseDao().deleteByIds(ids);
    }

    /**
     * 获取所有记录
     */
    public List<T> findAll(){
        return this.findAll();
    }

    /**
     * 根据id查询对象
     * @return
     */
    public T get(ID id){
        return getBaseDao().findOne(id);
    }

    /**
     * 非空字段集合查询
     * @param entity 查询对象
     * @return 集合
     */
    public List<T> findAll(T entity){
        return getBaseDao().findAll(getExample(entity));
    }

    /**
     * 非空字段集合查询
     * @param entity 查询对象
     * @param sort 排序条件
     * @return 集合
     */
    public List<T> findAll(T entity,Sort sort){
        return getBaseDao().findAll(getExample(entity),sort);
    }

    /**
     * 非空字段分页查询
     * @param entity 查询对象
     * @param pageable 分页对象
     * @return page
     */
    public Page<T> findAll(T entity,Pageable pageable){
        return getBaseDao().findAll(getExample(entity),pageable);
    }


    /**
     * 查询记录数
     * @param entity 查询条件
     * @return 记录数
     */
    public long count(T entity){
        return getBaseDao().count(getExample(entity));
    }


    private Example<T> getExample(T entity){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                ;
        return Example.of(entity, matcher);
    }
}
