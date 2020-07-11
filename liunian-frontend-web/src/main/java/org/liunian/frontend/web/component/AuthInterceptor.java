package org.liunian.frontend.web.component;

import org.liunian.common.JsonUtils;
import org.liunian.common.TokenSessionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(c -> "JSESSIONID".equalsIgnoreCase(c.getName())).findFirst();
        if (!cookieOptional.isPresent()) {
            response.sendRedirect("/admin/login");
            return false;
        }
        String token = cookieOptional.get().getValue();
        Object session = TokenSessionUtils.get(token);
        if (session == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        System.out.println(JsonUtils.to(session));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
