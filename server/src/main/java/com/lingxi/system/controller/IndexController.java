package com.lingxi.system.controller;


import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.system.entity.User;
import com.lingxi.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户个人信息
     */
    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    public ResultMsg info(HttpServletRequest request) {
        User user = userService.findByUserName(request.getRemoteUser());
        log.info(request.getRemoteUser());
        return ResultUtil.success(user);
    }

    /**
     * 保存用户个人信息
     */
    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResultMsg save(User newUser ,HttpServletRequest request) {
        User user = userService.findByUserName(request.getRemoteUser());
        //copy属性
        BeanUtils.copyProperties(newUser,user);
        return ResultUtil.success(this.userService.save(user));
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public ResultMsg changePassword(String password,String oldPassword ,HttpServletRequest request) {

        User user = userService.findByUserName(request.getRemoteUser());
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(password);
            userService.save(user);
            return ResultUtil.success(user);
        }

        return ResultUtil.failure("密码错误");
    }
}
