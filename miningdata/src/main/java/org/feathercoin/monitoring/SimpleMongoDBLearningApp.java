package org.feathercoin.monitoring;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.feathercoin.monitoring.domain.MiningStats;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class SimpleMongoDBLearningApp
{
    private static final Log log = LogFactory.getLog(SimpleMongoDBLearningApp.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {

        //MongoOperations mongoOps = new MongoTemplate(new Mongo("192.168.188.40"), "database");
        //MongoTemplate mongoTemplate = new MongoTemplate(new Mongo("localhost"), "database");
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add(new ServerAddress("192.168.188.40", 27017));
        seeds.add(new ServerAddress("192.168.188.40", 27018));
        seeds.add(new ServerAddress("192.168.188.51", 27017));
        seeds.add(new ServerAddress("192.168.188.51", 27018));



        Mongo mongo = new Mongo(new MongoURI("mongodb://192.168.188.40:27017,192.168.188.40:27018,192.168.188.51:27017,192.168.188.51:27018"));

        MongoTemplate mongoTemplate = new MongoTemplate(mongo, "database");
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/org/feathercoin/monitoring/dataApplicationContext.xml");
        mongoTemplate.setApplicationContext(applicationContext);
        MongoOperations mongoOps =  mongoTemplate;

        //mongoOps.dropCollection("feathercoinDailyMiningData");
        //insertData(mongoOps);


        TypedAggregation<FeathercoinDailyMiningData> agg = newAggregation(FeathercoinDailyMiningData.class,
                match(where("fetchTime").gte(sdf.parse("2013-12-01")).lte(sdf.parse("2013-12-31"))),
                group("fetchTime").max("dailyProduction").as("dailyProduction"),
                project()
                        .and("fetchTime").previousOperation()
                        .and("dailyProduction").as("dailyProduction"),
                sort(Sort.Direction.ASC,"fetchTime")


        );

        AggregationResults<MiningStats> results = mongoOps.aggregate(agg, MiningStats.class);
        List<MiningStats> miningDatas = results.getMappedResults();
        log.info(miningDatas);

        Query query = new Query(where("fetchTime").gte(sdf.parse("2013-12-28")));
        MapReduceResults<MiningDataValueObject> results2 = mongoOps.mapReduce(query,"feathercoinDailyMiningData",
                "classpath:/org/feathercoin/monitoring/mongodb/mapFunction.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceFunction.js",
                //new MapReduceOptions().
                MiningDataValueObject.class);


        for (MiningDataValueObject miningDataValueObject : results2) {
            System.out.println(miningDataValueObject);
        }


        MapReduceResults<HashMap> results3 = mongoOps.mapReduce("feathercoinDailyMiningData",
                "classpath:/org/feathercoin/monitoring/mongodb/mapUpdateDailyProduction.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceUpdateDailyProduction.js",
                //new MapReduceOptions().
                HashMap.class);

        for (HashMap hashMap : results3) {
            System.out.println(hashMap);
            FeathercoinDailyMiningData data = (FeathercoinDailyMiningData)hashMap.get("value");
            System.out.println(data);
        }


        FeathercoinRepository repository =  applicationContext.getBean(FeathercoinRepository.class);
        /*
        FeathercoinDailyMiningData data = repository.getLatestMiningDataEntry();
        System.out.println(data);

        WriteResult writeResult = repository.updateDailyProductionAndTotalCoinsMined(data.getId(),
                BigDecimal.valueOf(2000), BigDecimal.valueOf(4000));
        System.out.println(writeResult);



        data = repository.getLatestMiningDataEntry();
        System.out.println(data);

        FeathercoinStatsService feathercoinStatsService = applicationContext.getBean(FeathercoinStatsService.class);
        feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(5000.56));

        data = repository.getLatestMiningDataEntry();
        System.out.println(data);

        feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(5200.56));

        data = repository.getLatestMiningDataEntry();
        System.out.println(data);
        */


        //mongoOps.find(new Query(where("fetchTime").is(sdf.parse("2013-12-31 10:20:20"))).addCriteria(agg2));




        //insertData(mongoOps);
        /*
        log.info(mongoOps.findOne(new Query(where("dailyProduction").is(BigDecimal.valueOf(100))), FeathercoinDailyMiningData.class));
        log.info(mongoOps.find(new Query(where("dailyProduction").is(BigDecimal.valueOf(100))), FeathercoinDailyMiningData.class));
        log.info(mongoOps.find(new Query(where("dailyProduction").is(100d)), FeathercoinDailyMiningData.class));
        log.info(mongoOps.find(new Query(where("fetchTime").is(sdf.parse("2013-12-31 10:20:20"))), FeathercoinDailyMiningData.class));
        log.info(mongoOps.find(new Query(where("fetchTime").gt(sdf.parse("2013-11-30 10:20:20"))), FeathercoinDailyMiningData.class));
        */
                //mongoOps.dropCollection("person");


        //FeathercoinStatsService feathercoinStatsService = applicationContext.getBean(FeathercoinStatsService.class);
        //feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(5278.773));

        FeathercoinDailyMiningData data = repository.getLatestMiningDataEntry();
        System.out.println(data);
    }

    private static void insertData(MongoOperations mongoOps) {

        try {
            FeathercoinDailyMiningData[] toInsert = new FeathercoinDailyMiningData[]{
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(49.721),sdf.parse("2013-12-04")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(15.143),sdf.parse("2013-12-05")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(67.574),sdf.parse("2013-12-06")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(65.188),sdf.parse("2013-12-07")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(110.474),sdf.parse("2013-12-08")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(105.778),sdf.parse("2013-12-09")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(94.96),sdf.parse("2013-12-10")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(84.25),sdf.parse("2013-12-11")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(105.835),sdf.parse("2013-12-12")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(57.95),sdf.parse("2013-12-13")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(46.647),sdf.parse("2013-12-14")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(34.195),sdf.parse("2013-12-15")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(43.593),sdf.parse("2013-12-16")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(43.595),sdf.parse("2013-12-17")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(33.019),sdf.parse("2013-12-18")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(32.976),sdf.parse("2013-12-19")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(40.365),sdf.parse("2013-12-20")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(46.51),sdf.parse("2013-12-21")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(83.584),sdf.parse("2013-12-22")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(89.363),sdf.parse("2013-12-23")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(74.392),sdf.parse("2013-12-24")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(107.664),sdf.parse("2013-12-25")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(89.017),sdf.parse("2013-12-26")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(97.314),sdf.parse("2013-12-27")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(112.85),sdf.parse("2013-12-28")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(112.853),sdf.parse("2013-12-29")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(118.353),sdf.parse("2013-12-30")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(114.642),sdf.parse("2013-12-31")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(98.562),sdf.parse("2014-01-01")),

                    new FeathercoinDailyMiningData(BigDecimal.valueOf(90.357),BigDecimal.valueOf(5176.857),sdf.parse("2014-01-02")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(47.532),BigDecimal.valueOf(5176.857+47.532+54.384-53.532),sdf.parse("2014-01-03")),
                    new FeathercoinDailyMiningData(BigDecimal.valueOf(85.325),BigDecimal.valueOf(5176.857+47.532+54.384-53.532+85.325),sdf.parse("2014-01-04")),
            };
            //53.532
            for (FeathercoinDailyMiningData feathercoinDailyMiningData : toInsert) {

                mongoOps.insert(feathercoinDailyMiningData);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



    }
}
