package org.liunian.frontend.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.liunian.common.JsonUtils;
import org.springframework.context.annotation.Bean;

public class JacksonConfig {

    @Bean(name = "objectMapper")
    public ObjectMapper jacksonObjectMapper() {
        return JsonUtils.getDefaultMapper();
    }

}
