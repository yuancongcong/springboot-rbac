package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.system.dao.OrganizationDao;
import com.lingxi.system.entity.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationService extends CRUDService<Organization,Long> {

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public BaseRepository<Organization,Long> getBaseDao() {
        return organizationDao;
    }

}
