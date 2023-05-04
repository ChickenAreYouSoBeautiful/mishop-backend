package com.mi.mall1227.common.interceptor;

import com.mi.mall1227.common.util.JwtUtils;
import com.mi.mall1227.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println(path);
        if (handler instanceof HandlerMethod) {
            String token = request.getHeader("token");
            System.out.println("token=" + token);
            if (StringUtil.isEmpty(token)) {
                System.out.println("token为空！");
                throw new RuntimeException("签名验证不存在！");
            } else {
                // 如果token不为空 我们要对token鉴权
                Claims claims = JwtUtils.validateJWT(token).getClaims();
                if (path.startsWith("/admin")) {
                    if (claims == null || !"admin".equals(claims.getSubject()) || !"-1".equals(claims.getId())) {
                        throw new RuntimeException("管理员，鉴权失败");
                    } else {
                        System.out.println("鉴权成功");
                        return true;
                    }
                }
                if (claims == null) {
                    throw new RuntimeException("鉴权失败！");
                } else {
                    System.out.println("鉴权成功！");
                    return true;
                }
            }
        } else {
            return true;
        }
    }
}
