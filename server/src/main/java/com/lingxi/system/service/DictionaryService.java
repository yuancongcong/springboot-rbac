package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.framework.domain.callback.Callback;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.framework.utils.ArraysUtil;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.EmptyUtil;
import com.lingxi.system.dao.DictionaryDao;
import com.lingxi.system.entity.Dictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class DictionaryService extends CRUDService<Dictionary,Long> {

    private static final HashMap<String,List<Dictionary>> map = new HashMap<>();
    private static final HashMap<String,String> valueMap = new HashMap<>();

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public BaseRepository<Dictionary, Long> getBaseDao() {
        return dictionaryDao;
    }

    @Override
    public void deleteByIds(Long... ids) {
        //获取所有对象的上级ID
        List<Long> pids = ArraysUtil.map(Arrays.asList(ids),(Long id)-> super.get(id).getPid());

        //删除对象
        super.deleteByIds(ids);

        //修正上级的叶子节点
        Iterator<Long> iterator = pids.iterator();
        for(Long pid = iterator.next();iterator.hasNext();) {
            if(pid != null){
                //設置查詢條件
                Dictionary dictionary = new Dictionary();
                dictionary.setPid(pid);
                if(this.count(dictionary) == 0){ //是否存在下级记录
                    //修改为叶子节点
                    Dictionary parent = get(pid);
                    parent.setIsLeaf(true);
                    super.save(parent);
                }
            }
        }

        map.clear(); //清楚缓存数据
    }

    @Override
    public Dictionary save(Dictionary entity) {

        boolean bool = entity.getId() == null;

        if(bool){//是否新增
            entity.setIsLeaf(true);
            entity = super.save(entity);
        }

        if(EmptyUtil.isNotEmpty(entity.getPid())){
            //修改父级节点
            Dictionary parent = get(entity.getPid());
            parent.setIsLeaf(false);
            this.dictionaryDao.save(parent);
            //设置级别和标识
            entity.setLevel(parent.getLevel()+1);
            entity.setFlag(parent.getFlag()+ "." + entity.getId());
        }else{
            //设置级别和标识
            entity.setFlag(""+entity.getId());
            entity.setLevel(1);
        }
        entity = super.save(entity);


        map.clear(); //清楚缓存数据
        return entity;
    }

    @Override
    public Page<Dictionary> findAll(Dictionary entity, Pageable pageable) {
        return dictionaryDao.findAll((Root<Dictionary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
            new SimpleQuery(cb,root)
                .llike("flag",entity.getFlag())
                .or(
                        new SimpleQuery(cb,root)
                                .like("name",entity.getName()) //name like '%:value%'
                                .like("code",entity.getName()) //code like '%:value%'
                        .toPredicates()
                )
                .toPredicate()
        ,pageable);
    }

    @Override
    public List<Dictionary> findAll(Dictionary entity) {
        return this.dictionaryDao.findAll((Root<Dictionary> root, CriteriaQuery<?> query, CriteriaBuilder cb)->
             new SimpleQuery(cb,root)
                    .eqOrIsNull("pid",entity.getPid())
                    .toPredicate()
        ,new Sort(new Sort.Order(Sort.Direction.DESC,"sort")));
    }

    /**
     * 根据上级CODE查询下级
     * @param type 上级code
     * @param isRepeat 是否查询所有下级
     * @return
     */
    public List<Dictionary> findByParentCode(String type,boolean isRepeat) {

        if(map.containsKey(type)){  //优先从缓存拿取数据
            return map.get(type);
        }

        List<Dictionary> dictList = this.dictionaryDao.findByParentCode(type);
        if(isRepeat){
            //递归所有下级数据
            new Callback<List<Dictionary>, List<Dictionary>>() {
                @Override
                public List<Dictionary> call(List<Dictionary> subMenus) {
                    for(Dictionary dict : subMenus){
                        Dictionary query = new Dictionary();
                        query.setPid(dict.getId());
                        if(dict.getIsLeaf()==null || !dict.getIsLeaf()){
                            List<Dictionary> children = findAll(query);
                            dict.setChildren(children);
                            this.call(children);
                        }
                    }
                    return dictList;
                }
            }.call(dictList);
        }

        map.put(type,dictList);//添加缓存数据
        return dictList;
    }

    /**
     * 获取字典名称name
     * @return
     */
    public static String getValue(String key){
        if(valueMap.containsKey(key)){
            return valueMap.get(key);
        }

        DictionaryDao dao = BeanUtils.getBean(DictionaryDao.class);

        return dao.findByCode(key).getName();
    }
}
