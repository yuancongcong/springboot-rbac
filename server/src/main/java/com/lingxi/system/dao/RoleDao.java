package com.lingxi.system.dao;


import com.lingxi.framework.base.BaseRepository;
import com.lingxi.system.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseRepository<Role,Long> {

    @Query("select role from Role role left join role.users user where user.userName= ?1")
    List<Role> findByUserName(String userName);

}