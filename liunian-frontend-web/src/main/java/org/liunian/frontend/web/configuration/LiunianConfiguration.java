package org.liunian.frontend.web.configuration;

import org.liunian.common.SpringContextUtils;
import org.liunian.frontend.web.listener.ApplicationEventAdvice;
import org.liunian.frontend.web.listener.WebServerListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        WebServerListener.class,
        ApplicationEventAdvice.class,
        JacksonConfig.class,
        SpringContextUtils.class,
        BalanceConfig.class,
        RedisConfig.class})
@ComponentScan(basePackages = {"org.liunian.frontend.client.fallbackfactory"})
public class LiunianConfiguration {
}
