package com.lingxi.security;

import com.lingxi.security.domain.SecurityUser;
import com.lingxi.system.dao.RoleDao;
import com.lingxi.system.dao.UserDao;
import com.lingxi.system.entity.Role;
import com.lingxi.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public UserDetails loadUserByUsername(String userName) {
        User user = userDao.findByUserName(userName);
        if (user != null) {
            List<Role> roles = roleDao.findByUserName(userName);
            user.setRoles(roles);
            return new SecurityUser(user);
        } else {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
    }
}