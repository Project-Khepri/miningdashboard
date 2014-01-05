package org.feathercoin.monitoring;


import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@PropertySource({"classpath:defaultAppConfig.properties", "classpath:myAppConfig.properties"})
class MongoConfiguration extends AbstractMongoConfiguration {
    @Value("${mongo.dburl}") String mongoDbUrl;


    @Override
    protected String getDatabaseName() {
        return "database";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo(new MongoURI(mongoDbUrl));
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.feathercoin.monitoring";
    }


}
