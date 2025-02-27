package org.liunian.frontend.client.fallbackfactory;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.liunian.frontend.client.client.ProductApiClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class ProductApiFallbackFactory implements FallbackFactory<ProductApiClient> {

    @Override
    public ProductApiClient create(Throwable throwable) {
        String[] stackTraceArray = Arrays.stream(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
        log.error("调用远程服务发生异常。\r\n异常消息：{}\r\n\r\tat {}", throwable.toString(), String.join("\r\n\r\tat ", stackTraceArray));
        return new ProductApiFallback();
    }

}
