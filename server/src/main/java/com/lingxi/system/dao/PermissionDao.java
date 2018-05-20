package com.lingxi.system.dao;


import com.lingxi.framework.base.BaseRepository;
import com.lingxi.system.entity.Permission;
import com.lingxi.system.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends BaseRepository<Permission,Long> {

    List<Permission> findByRoles(List<Role> roles);
}