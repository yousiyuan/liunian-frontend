package org.liunian.frontend.client.client;

import org.liunian.frontend.client.fallbackfactory.ProductApiFallbackFactory;
import org.liunian.frontend.dto.Product;
import org.liunian.frontend.to.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SPARROW-API-SAMPLE", path = "/api/data/product", fallbackFactory = ProductApiFallbackFactory.class, contextId = "productApi")
public interface ProductApiClient {

    @GetMapping("/list")
    BaseResult<List<Product>> productList();

    @GetMapping("/update/{id}")
    String exceptionTest(@PathVariable("id") Integer id);

}
