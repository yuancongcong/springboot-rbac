package com.lingxi.system.dao;


import com.lingxi.framework.base.BaseRepository;
import com.lingxi.system.entity.Organization;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao extends BaseRepository<Organization,Long> {

}