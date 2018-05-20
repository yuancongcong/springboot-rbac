package com.lingxi.security.utils;

import com.lingxi.framework.domain.ResultMsg;
import com.lingxi.framework.utils.ResultUtil;
import com.lingxi.security.domain.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JWTUtil {
    public static final long EXPIRATION_TIME = 432_000_000;     // 5天
    public static final String SECRET = "P@ssw02d";            // JWT密码
    public static final String TOKEN_PREFIX = "lingxi";        // Token前缀
    public static final String TOKEN_STRING = "x-auth-token";// 存放Token的 Key

    public static String getToken(HttpServletRequest request){
        return request.getParameter(TOKEN_STRING);
    }

    // JWT生成方法
    public static void addAuthentication(HttpServletResponse response, Authentication auth) {
        SecurityUser user = (SecurityUser) auth.getPrincipal();

        StringBuffer sb = new StringBuffer();
        for(GrantedAuthority gd : user.getAuthorities()){
            sb.append(gd.getAuthority()+",");
        }
        // 生成JWT
        String JWT = Jwts.builder()
                // 保存权限（角色）
                .claim("authorities", sb.toString())
                // 用户名写入标题
                .setSubject(user.getUsername())
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        // 将 JWT 写入 body
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            ResultMsg message = ResultUtil.success(JWT);
            ResultUtil.responsePrint(response,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JWT验证方法
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = getToken(request);
        if (token != null) {
            // 解析 token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            // 拿用户名
            String user = claims.getSubject();
            // 得到 权限（角色）
            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

            // 返回验证令牌
            return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
        }
        return null;
    }
}
