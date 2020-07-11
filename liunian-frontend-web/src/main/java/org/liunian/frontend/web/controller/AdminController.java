package org.liunian.frontend.web.controller;

import org.liunian.common.ComUtils;
import org.liunian.common.TokenSessionUtils;
import org.liunian.frontend.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/admin/index");
    }

    @GetMapping("/header")
    public ModelAndView header() {
        return new ModelAndView("/admin/header");
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("/admin/main");
    }

    @GetMapping("/menu")
    public ModelAndView menu() {
        return new ModelAndView("/admin/menu");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/admin/login");
    }

    @PostMapping("/login/submit")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        if (!ComUtils.isNotEmpty(user) || !ComUtils.isNotEmpty(password)) {
            System.out.println("用户名或密码不能为空");
            return new ModelAndView(new RedirectView("/admin/login"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("password", password);

        // 创建Session
        String token = TokenSessionUtils.put(map);
        response.setHeader("JSESSIONID", token);

        Cookie tokenCookie = new Cookie("JSESSIONID", token);
        tokenCookie.setMaxAge(60 * 18);
        tokenCookie.setPath(request.getContextPath() + "/");
        response.addCookie(tokenCookie);

        return new ModelAndView(new RedirectView("/admin/"));
    }

}
