package org.liunian.frontend.client.fallbackfactory;

import lombok.extern.slf4j.Slf4j;
import org.liunian.frontend.client.client.ThirdPartyApiClient;

@Slf4j
public class ThirdPartyApiFallback implements ThirdPartyApiClient {

    @Override
    public String queryIpAddressInfo(String key) {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return " ";
    }

}
