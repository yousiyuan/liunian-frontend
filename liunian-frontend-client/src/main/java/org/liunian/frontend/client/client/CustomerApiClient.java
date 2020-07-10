package org.liunian.frontend.client.client;

import org.liunian.frontend.client.fallbackfactory.CustomerApiFallbackFactory;
import org.liunian.frontend.dto.Customer;
import org.liunian.frontend.to.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SPARROW-API-SAMPLE", path = "/api/data/customer", fallbackFactory = CustomerApiFallbackFactory.class, contextId = "customerApi")
public interface CustomerApiClient {

    @GetMapping("/list")
    BaseResult<List<Customer>> customerList();

    @GetMapping("/update/{id}")
    String exceptionTest(@PathVariable("id") Integer id);

}
