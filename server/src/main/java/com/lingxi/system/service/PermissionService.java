package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.security.UrlInvocationSecurityMetadataSourceService;
import com.lingxi.system.dao.PermissionDao;
import com.lingxi.system.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@Slf4j
public class PermissionService extends CRUDService<Permission,Long> {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public BaseRepository<Permission, Long> getBaseDao() {
        return permissionDao;
    }

    @Override
    public Permission save(Permission entity) {
        //清除资源缓存
        UrlInvocationSecurityMetadataSourceService.clean();
        return super.save(entity);
    }

    @Override
    public Page<Permission> findAll(Permission entity, Pageable pageable) {
        return permissionDao.findAll((Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
            new SimpleQuery(cb,root)
                .like("name",entity.getName()) //name like '%:value%'
                .toPredicate()
        ,pageable);
    }
}
