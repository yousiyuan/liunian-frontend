package org.liunian.frontend.web.controller.base;

import org.liunian.frontend.client.client.CustomerApiClient;
import org.liunian.frontend.client.client.ProductApiClient;
import org.liunian.frontend.client.client.ThirdPartyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected static final String APP_KEY = "3BFBZ-ZKD3X-LW54A-ZT76D-E7AHO-4RBD5";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    protected ThirdPartyApiClient thirdPartyApiClient;

    @Autowired
    protected CustomerApiClient customerApiClient;

    @Autowired
    protected ProductApiClient productApiClient;

    protected HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return this.request;
    }

    @Value("${server.port}")
    protected Integer serverPort;

}
