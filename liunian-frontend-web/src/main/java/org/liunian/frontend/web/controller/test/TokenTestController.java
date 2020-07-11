package org.liunian.frontend.web.controller.test;

import org.liunian.common.token.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/token")
public class TokenTestController {

    @GetMapping("/get")
    @ResponseBody
    public String getRemoteContent() {
        String s = UUID.randomUUID().toString();
        String sdt = JwtUtils.createTokenByRS256(s);
        System.out.println(JwtUtils.verifyToken(sdt));
        System.out.println(JwtUtils.parseToken(sdt).getClaim("data").asString());
        return sdt;
    }

}
