package org.liunian.frontend.client.client;

import org.liunian.frontend.client.fallbackfactory.ThirdPartyApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//注意：这里可以通过SPEL表达式读取到Apollo中的配置信息，但无法刷新本FeignClient实例，无论是使用FeignClient调用第三方服务或者调用本地微服务，都是同样
@FeignClient(name = "thirdPartyApiClient", url = "${web.business.pandaUrl}", fallbackFactory = ThirdPartyApiFallbackFactory.class)
public interface ThirdPartyApiClient {

    @GetMapping("/location/v1/ip")
    String queryIpAddressInfo(@RequestParam("key") String key);

}
