package org.feathercoin.monitoring;


import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableMongoRepositories
@PropertySource({"classpath:defaultAppConfig.properties", "classpath:myAppConfig.properties"})
class MongoConfiguration extends AbstractMongoConfiguration {
    @Value("${mongo.dburl}") String mongoDbUrl;
    @Value("${mongo.loggingLevel}") String loggingLevel;


    @Override
    protected String getDatabaseName() {
        return "database";
    }

    @Override
    public Mongo mongo() throws Exception {
        Logger logger = Logger.getLogger("com.mongodb");
        logger.setLevel(Level.parse(loggingLevel));
        return new Mongo(new MongoURI(mongoDbUrl));
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.feathercoin.monitoring";
    }





}
