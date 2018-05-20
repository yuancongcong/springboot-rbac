package com.lingxi.security;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResultMsg message = ResultUtil.failure("权限不足，请联系管理员!","auth-failure");
        ResultUtil.responsePrint(response,message);
    }
}
