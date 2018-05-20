package com.lingxi.security.filter;

import com.lingxi.security.utils.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        //判断用户是否登录
        String token = JWTUtil.getToken(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        //身份认证
        SecurityContextHolder.getContext().setAuthentication(JWTUtil.getAuthentication(request));
        chain.doFilter(request, response);
    }
}