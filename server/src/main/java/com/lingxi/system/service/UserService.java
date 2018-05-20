package com.lingxi.system.service;

import com.lingxi.framework.base.BaseRepository;
import com.lingxi.framework.base.CRUDService;
import com.lingxi.framework.domain.jpa.SimpleQuery;
import com.lingxi.framework.utils.EmptyUtil;
import com.lingxi.framework.utils.MD5Util;
import com.lingxi.system.dao.UserDao;
import com.lingxi.system.entity.Role;
import com.lingxi.system.entity.User;
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
public class UserService extends CRUDService<User,Long> {

    @Autowired
    private UserDao userDao;

    @Override
    public BaseRepository getBaseDao() {
        return userDao;
    }

    @Override
    public User save(User entity) {

        if(EmptyUtil.isNotEmpty(entity.getPassword()) && entity.getPassword().length() != 32){ //判断当前密码是否已经MD5加密
            //HD5加密
            entity.setPassword(MD5Util.encode(entity.getPassword()));
        }
        return super.save(entity);
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public List<User> findByRoles(List<Role> roles) {
        return userDao.findByRoles(roles);
    }

    public Page<User> getPageList(final User user, Pageable pageable) {
        return userDao.findAll((Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)->
                        new SimpleQuery(cb,root)
                                .like("userName",user.getUserName()) //userName like '%:value%'
                                .like("realName",user.getRealName()) //realName like '%:value%'
                                .toPredicate()
        ,pageable);
    }
}
