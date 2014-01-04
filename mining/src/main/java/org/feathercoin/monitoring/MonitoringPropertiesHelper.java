package org.feathercoin.monitoring;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;

public class MonitoringPropertiesHelper {
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer pspc =
                new PropertySourcesPlaceholderConfigurer();
        ArrayList<Resource> properties = new ArrayList<Resource>();
        properties.add(new ClassPathResource( "/defaultAppConfig.properties" ));

        ClassPathResource privateAppConfig = new ClassPathResource("/myAppConfig.properties");
        if (privateAppConfig.exists())
            properties.add(privateAppConfig);

        pspc.setLocations( properties.toArray(new Resource[]{}) );
        pspc.setNullValue("@null");

        pspc.setIgnoreUnresolvablePlaceholders( true );
        return pspc;
    }
}
