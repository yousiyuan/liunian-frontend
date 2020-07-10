package org.liunian.frontend.web.controller;

import org.liunian.common.JsonUtils;
import org.liunian.frontend.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

//session.invalidate();  使当前session会话失效
@Controller
@RequestMapping("/sess")
public class SessionTestController extends BaseController {

    //region 单个JVM中Session的使用
    @GetMapping("/set")
    @ResponseBody
    public Object setSessionTest() {
        HttpServletRequest request = this.getRequest();
        // request.getSession()的解读
        // 1.如果客户端浏览器Cookie中：存放了SessionID，就将SessionID放在请求头中，读取本地的Session缓存；
        // 2.如果客户端浏览器Cookie中：没有存放SessionID，服务器端默认创建一个Session，并将SessionID放在响应体中，
        //   返回给客户端浏览器保存在Cookie中；
        // 3.如果客户端浏览器Cookie中 存放的SessionID，在服务器端没有找到对应的Session，则服务器端会默认创建一个Session，
        //   返回给客户端浏览器保存在Cookie中；
        HttpSession session = request.getSession();
        session.setAttribute("id", 31);
        session.setAttribute("name", "zxb");
        return serverPort + " - 设置session(" + session.getId() + ")";
    }

    @GetMapping("/get")
    @ResponseBody
    public Object getSessionTest() {
        HttpServletRequest request = this.getRequest();
        // request.getSession()的解读
        // 1.如果客户端浏览器Cookie中：存放了SessionID，就将SessionID放在请求头中，读取本地的Session缓存；
        // 2.如果客户端浏览器Cookie中：没有存放SessionID，服务器端默认创建一个Session，并将SessionID放在响应体中，
        //   返回给客户端浏览器保存在Cookie中；
        // 3.如果客户端浏览器Cookie中 存放的SessionID，在服务器端没有找到对应的Session，则服务器端会默认创建一个Session，
        //   返回给客户端浏览器保存在Cookie中；
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>();
        map.put("id", session.getAttribute("id"));
        map.put("name", session.getAttribute("name"));
        return serverPort + " - 获取session(" + session.getId() + ")<br/>" + JsonUtils.to(map);
    }
    //endregion

    //request.getSession()与request.getSession(false)的区别

    //region 分布式Session
    @GetMapping("/set2")
    @ResponseBody
    public Object setSessionTest2() {
        HttpServletRequest request = this.getRequest();
        // request.getSession(false)的解读
        // 1.如果客户端浏览器的Cookie中：没有存放SessionID，那么，服务器端 也不会 主动创建Session；
        // 2.如果客户端浏览器的Cookie中：存放了SessionID，在服务器端没有找到对应的Session，那么，服务器端 也不会 主动创建Session；
        // 3.在分布式集群环境下，通过这种方式，使服务器端不会主动创建Session，也就不会产生新的SessionID，
        //   这样，在相同Cookie存放路径下，可以避免新的SessionID覆盖客户端浏览器的Cookie中已经存放的SessionID。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("id", 32);
            session.setAttribute("name", "流年");
            return serverPort + " - 设置session(" + session.getId() + ")";
        } else if (request.getCookies() == null) {
            return serverPort + " - 没有找到Session";
        }
        return Arrays.stream(request.getCookies())
                .filter(cok -> "JSESSIONID".equals(cok.getName()))
                .findFirst()
                .map(cookie -> serverPort + " - 没有找到Session(" + cookie.getValue() + ")")
                .orElseGet(() -> serverPort + " - 没有找到Session");
    }

    @GetMapping("/get2")
    @ResponseBody
    public Object getSessionTest2() {
        HttpServletRequest request = this.getRequest();
        // request.getSession(false)的解读
        // 1.如果客户端浏览器的Cookie中：没有存放SessionID，那么，服务器端 也不会 主动创建Session；
        // 2.如果客户端浏览器的Cookie中：存放了SessionID，在服务器端没有找到对应的Session，那么，服务器端 也不会 主动创建Session；
        // 3.在分布式集群环境下，通过这种方式，使服务器端不会主动创建Session，也就不会产生新的SessionID，
        //   这样，在相同Cookie存放路径下，可以避免新的SessionID覆盖客户端浏览器的Cookie中已经存放的SessionID。
        HttpSession session = request.getSession(false);
        if (session != null) {
            Enumeration<String> attributeNames = session.getAttributeNames();
            Map<String, Object> map = new HashMap<>();
            while (attributeNames.hasMoreElements()) {
                String key = attributeNames.nextElement();
                map.put(key, session.getAttribute(key));
            }
            return serverPort + " - 获取session(" + session.getId() + ")<br/>" + JsonUtils.to(map);
        } else if (request.getCookies() == null) {
            return serverPort + " - 没有找到Session";
        }
        return Arrays.stream(request.getCookies())
                .filter(cok -> "JSESSIONID".equals(cok.getName()))
                .findFirst()
                .map(cookie -> serverPort + " - 没有找到Session(" + cookie.getValue() + ")")
                .orElseGet(() -> serverPort + " - 没有找到Session");
    }
    //endregion

}
