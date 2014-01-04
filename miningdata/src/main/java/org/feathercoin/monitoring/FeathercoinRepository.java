package org.feathercoin.monitoring;

import com.mongodb.WriteResult;
import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
public class FeathercoinRepository {
    @Autowired private MongoTemplate mongoTemplate;

    public FeathercoinDailyMiningData getLatestMiningDataEntry(){
        MapReduceResults<HashMap> result = mongoTemplate.mapReduce("feathercoinDailyMiningData",
                "classpath:/org/feathercoin/monitoring/mongodb/mapUpdateDailyProduction.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceUpdateDailyProduction.js",
                HashMap.class);

        for (HashMap hashMap : result) {
            FeathercoinDailyMiningData data = (FeathercoinDailyMiningData)hashMap.get("value");
            return data;
        }
        return null;
    }

    public WriteResult updateDailyProductionAndTotalCoinsMined(String id, BigDecimal dailyProduction, BigDecimal totalCoinsMined){
        Query query = new Query(where("id").is(id));
        WriteResult writeResult = mongoTemplate.updateFirst(query,
                update("dailyProduction", dailyProduction.doubleValue())
                .set("totalCoinsMined",totalCoinsMined.doubleValue()), FeathercoinDailyMiningData.class);
        return writeResult;
    }

    public void insertDailyProductionEntry(FeathercoinDailyMiningData feathercoinDailyMiningData){
        mongoTemplate.insert(feathercoinDailyMiningData);
    }

    public List<MiningStats> getMiningStats(Interval interval){
        Query query = new Query(where("fetchTime").gte(interval.getStart().toDate()).lte(interval.getEnd().toDate()));
        MapReduceResults<MiningDataValueObject> results = mongoTemplate.mapReduce(query, "feathercoinDailyMiningData",
                "classpath:/org/feathercoin/monitoring/mongodb/mapFunction.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceFunction.js",
                MiningDataValueObject.class);

        List<MiningStats> miningDatas = new ArrayList<MiningStats>();
        for (MiningDataValueObject miningDataValueObject : results) {
            miningDatas.add(new MiningStats(BigDecimal.valueOf(miningDataValueObject.getValue()),
                    miningDataValueObject.getId()));
        }

        return miningDatas;
    }

    public List<MiningStats> getMiningStatsCurrentMonth(){
        Interval interval = DateUtil.getIntervalForCurrentMonth();

        return getMiningStats(interval);
    }

    public List<MiningStats> getMiningStatsLast30Days(){
        Interval interval = DateUtil.getIntervalForLast30Days();

        return getMiningStats(interval);
    }
}
