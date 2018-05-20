package com.lingxi.security.filter;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.BeanUtils;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.framework.utils.TaskUtils;
import com.lingxi.security.domain.SecurityUser;
import com.lingxi.security.utils.JWTUtil;
import com.lingxi.system.entity.User;
import com.lingxi.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = req.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        JWTUtil.addAuthentication(response,auth);
        //异步修改登录时间
        TaskUtils.executor(()-> {
                UserService userService = BeanUtils.getBean(UserService.class);
                SecurityUser securityUser = (SecurityUser)auth.getPrincipal();
                User user =  userService.findByUserName(securityUser.getUsername());
                user.setLastLoginTime(new Date());
                userService.save(user);
                log.info("登录成功",securityUser.getUsername());
        });
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResultMsg message ;
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            message = ResultUtil.failure("用户名或密码输入错误，登录失败!");
            log.info("用户名或密码输入错误，登录失败!",username);
        } else if (exception instanceof DisabledException) {
            message = ResultUtil.failure("账户被禁用，登录失败，请联系管理员!");
            log.info("账户被禁用，登录失败，请联系管理员!",username);
        } else {
            message = ResultUtil.failure("登录失败!");
            log.info("登录失败!",username);
        }
        ResultUtil.responsePrint(response,message);
    }
}
