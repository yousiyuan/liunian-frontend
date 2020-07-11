package org.liunian.frontend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

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

}
