package org.feathercoin.monitoring;

import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.feathercoin.monitoring.json.MinerUdpCall;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class WebAppConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer pspc =
                new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                {       new ClassPathResource( "/defaultAppConfig.properties" ),
                        new ClassPathResource( "/myAppConfig.properties" ),
                };
        pspc.setLocations( resources );
        pspc.setNullValue("@null");
        pspc.setIgnoreUnresolvablePlaceholders( true );
        return pspc;
    }

    @Bean
    public static MinerConnections getMinerConnections(){
        MinerConnections minerConnections = new MinerConnections();
        ClassPathResource defaultMinerConfig = new ClassPathResource("/minerProperties.json");
        ClassPathResource myMinerConfig = new ClassPathResource("/myMinerProperties.json");

        String result = myMinerConfig.exists()?readResourceToString(myMinerConfig):readResourceToString(defaultMinerConfig);

        JsonResponseTransformer<MinerJsonConfig> minerJsonConfigTransformer =
                new JsonResponseTransformer<MinerJsonConfig>(MinerJsonConfig.class);

        MinerJsonConfig minerJsonConfig = minerJsonConfigTransformer.transform(result);
        if (minerJsonConfig==null)
            return minerConnections;

        List<MinerConnectionConfig> miners = minerJsonConfig.getMiners();
        for (MinerConnectionConfig minerConnectionConfig : miners) {
            minerConnections.addMiner(new MinerUdpCall(minerConnectionConfig.getIp(),minerConnectionConfig.getPort()));
        }

        return minerConnections;
    }

    private static String readResourceToString(ClassPathResource resourceToLoad) {
        if (!resourceToLoad.exists())
            return null;

        String result = null;
        try {
            InputStream inputStream = resourceToLoad.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] read = new byte [10000];

            while(true){
                int readCount = inputStream.read(read);
                if (readCount==-1)
                    break;
                byteArrayOutputStream.write(read,0,readCount);
            }
            result = new String(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
