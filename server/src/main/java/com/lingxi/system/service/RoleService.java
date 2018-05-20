package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.framework.utils.ArraysUtil;
import com.lingxi.system.dao.RoleDao;
import com.lingxi.system.entity.Menu;
import com.lingxi.system.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Slf4j
public class RoleService extends CRUDService<Role,Long> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public BaseRepository getBaseDao() {
        return roleDao;
    }

    public List<Role> findByUserName(String userName) {
        return roleDao.findByUserName(userName);
    }

    @Override
    public List<Role> findAll(Role entity) {
        return roleDao.findAll((Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                new SimpleQuery(cb,root)
                        .like("name",entity.getName()) //name like '%:value%'
                        .in("menus.id", ArraysUtil.map(entity.getMenus(), (Menu menu) -> menu.getId()))
                        .toPredicate()
        );
    }


    @Override
    public Page<Role> findAll(Role role, Pageable pageable) {
        return roleDao.findAll((Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                new SimpleQuery(cb,root)
                        .eq("disabled",role.getDisabled())
                        .like("name",role.getName()) //name like '%:value%'
                        .toPredicate()
        ,pageable);
    }
}
