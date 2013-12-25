package org.feathercoin.monitoring;

import com.mongodb.Mongo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.feathercoin.monitoring.domain.Stock;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Log log = LogFactory.getLog(App.class);

    public static void main(String[] args) throws Exception {

        MongoOperations mongoOps = new MongoTemplate(new Mongo("192.168.188.40"), "database");

        //mongoOps.insert(new Stock("ID123", BigDecimal.TEN));

        log.info(mongoOps.findOne(new Query(where("value").is(BigDecimal.TEN)), Stock.class));

        //mongoOps.dropCollection("person");
    }
}
