package org.liunian.frontend.web.controller;

import org.liunian.frontend.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/home/index");
    }

    @GetMapping("/ip")
    @ResponseBody
    public String ip() {
        return thirdPartyApiClient.queryIpAddressInfo(APP_KEY);
    }

}
