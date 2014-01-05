package org.feathercoin.monitoring;


import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories
class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "database";
    }

    @Override
    public Mongo mongo() throws Exception {
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add(new ServerAddress("192.168.188.40", 27017));
        seeds.add(new ServerAddress("192.168.188.40", 27018));
        seeds.add(new ServerAddress("192.168.188.51", 27017));
        seeds.add(new ServerAddress("192.168.188.51", 27018));
        return new Mongo(seeds);
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.feathercoin.monitoring";
    }


}
