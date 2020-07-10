package org.liunian.frontend.client.fallbackfactory;

import lombok.extern.slf4j.Slf4j;
import org.liunian.frontend.client.client.CustomerApiClient;
import org.liunian.frontend.dto.Customer;
import org.liunian.frontend.enums.ReturnEnum;
import org.liunian.frontend.to.BaseResponse;
import org.liunian.frontend.to.BaseResult;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerApiFallback implements CustomerApiClient {

    @Override
    public BaseResult<List<Customer>> customerList() {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return BaseResponse.error(ReturnEnum.ERROR, new ArrayList<>());
    }

    @Override
    public String exceptionTest(Integer id) {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return null;
    }

}
