package org.feathercoin.monitoring;

import org.feathercoin.monitoring.json.rest.JsonRequestExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class BatchConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        return MonitoringPropertiesHelper.properties();
    }

    @Bean
    public static JsonRequestExecutor configureJSONRequestExecutor(){
        JsonRequestExecutor jsonRequestExecutor = new JsonRequestExecutor();
        jsonRequestExecutor.setProtocol("https");
        jsonRequestExecutor.setPort(443);
        return jsonRequestExecutor;

    }

}
