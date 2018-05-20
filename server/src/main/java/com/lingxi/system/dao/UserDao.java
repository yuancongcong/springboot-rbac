package com.lingxi.system.dao;


import com.lingxi.framework.base.BaseRepository;
import com.lingxi.system.entity.Role;
import com.lingxi.system.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseRepository<User,Long> {

    User findByUserName(String userName);

    List<User> findByRoles(List<Role> roles) ;
}