package com.lingxi.system.dao;


import com.lingxi.framework.base.BaseRepository;
import com.lingxi.system.entity.Dictionary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryDao extends BaseRepository<Dictionary,Long> {

    @Query("select t from Dictionary t where t.pid in (select id from Dictionary t1 where t1.code = ?1)")
    List<Dictionary> findByParentCode(String parentCode);

    Dictionary findByCode(String code);
}