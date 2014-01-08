package org.feathercoin.monitoring;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;

/**
 * <p>Class to load configuration properties from the class path. To be used in the configuration.
 * It first loads the /defaultAppConfig.properties containing a default configuration and is available through source
 * distribution.</p>
 * <p>
 * Default properties are overridden by entries in the properties file /myAppConfig.properties.
 * myAppConfig.properties is not distributed via source distribution and should be used for local configuration changes
 * (e.g. your own miner configuration, FTC address, Pool API-keys,...)</p>
 * <p>The properties need to be available through the classpath.</p>
 */
public class MonitoringPropertiesHelper {
    /**
     * Reads the properties from /defaultAppConfig.properties and /myAppConfig.properties (if available)
     * Sets the default null value to @null (to be used in @Value annotations ... more details see Spring docu)
     * @return PropertySourcesPlaceholderConfigurer to inject properties in the spring context
     */
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
