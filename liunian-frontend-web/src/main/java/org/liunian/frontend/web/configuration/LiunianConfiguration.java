package org.liunian.frontend.web.configuration;

import org.liunian.common.SpringContextUtils;
import org.liunian.frontend.web.component.CharacterEncodingFilter;
import org.liunian.frontend.web.component.AuthInterceptor;
import org.liunian.frontend.web.component.ApplicationEventAdvice;
import org.liunian.frontend.web.component.WebServerListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.HashMap;

@Configuration
@Import(value = {
        WebServerListener.class,
        ApplicationEventAdvice.class,
        JacksonConfig.class,
        SpringContextUtils.class,
        BalanceConfig.class,
        RedisConfig.class})
@ComponentScan(basePackages = {"org.liunian.frontend.client.fallbackfactory"})
public class LiunianConfiguration implements WebMvcConfigurer {

    // 注册字符编码过滤器
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> charsetFilterRegister() {
        FilterRegistrationBean<CharacterEncodingFilter> charsetFilterRegister = new FilterRegistrationBean<>();
        charsetFilterRegister.setFilter(new CharacterEncodingFilter());//设置过滤器对象
        charsetFilterRegister.setUrlPatterns(Collections.singletonList("/*"));//设置过滤器拦截的Servlet或者MVC处理器
        charsetFilterRegister.setName("charsetFilter");//设置filter名称

        charsetFilterRegister.setInitParameters(new HashMap<String, String>() {{
            put("charset", "UTF-8");
        }});
        charsetFilterRegister.setOrder(1);//设置启动顺序

        return charsetFilterRegister;
    }

    // 注册身份验证拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/**/login")
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg");
    }

    // 添加静态资源文件，外部可以直接访问地址
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
