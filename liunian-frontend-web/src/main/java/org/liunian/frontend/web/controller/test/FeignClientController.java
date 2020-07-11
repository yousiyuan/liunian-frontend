package org.liunian.frontend.web.controller.test;

import org.liunian.common.JsonUtils;
import org.liunian.frontend.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class FeignClientController extends BaseController {

    @GetMapping("/ip")
    @ResponseBody
    public String getHostAddress() {
        return thirdPartyApiClient.queryIpAddressInfo(APP_KEY);
    }

    @GetMapping("/data")
    @ResponseBody
    public String getRemoteContent() {
        Map<String, Object> result = new HashMap<>();
        result.put("product_list", productApiClient.productList());
        result.put("customer_list", customerApiClient.customerList());
        return JsonUtils.to(result);
    }

}
