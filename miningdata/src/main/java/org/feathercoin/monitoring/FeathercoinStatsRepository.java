package org.feathercoin.monitoring;

import com.mongodb.WriteResult;
import org.feathercoin.monitoring.domain.DailyStats;
import org.feathercoin.monitoring.domain.FeathercoinDailyStats;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
public class FeathercoinStatsRepository {
    @Autowired private MongoTemplate mongoTemplate;

    public FeathercoinDailyStats getLatestStatsEntry(){
        MapReduceResults<HashMap> result = mongoTemplate.mapReduce("feathercoinDailyStats",
                "classpath:/org/feathercoin/monitoring/mongodb/mapDailyStats.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceDailyStats.js",
                HashMap.class);

        for (HashMap hashMap : result) {
            FeathercoinDailyStats data = (FeathercoinDailyStats)hashMap.get("value");
            return data;
        }
        return null;
    }

    public WriteResult updateDailyStats(String id,
                                                               BigDecimal price, BigDecimal difficulty,
                                                               BigDecimal netRevenue){
        Query query = new Query(where("id").is(id));
        WriteResult writeResult = mongoTemplate.updateFirst(query,
                update("price", scaleTo3(price).doubleValue())
                        .set("difficulty", scaleTo3(difficulty).doubleValue())
                        .set("netRevenue", scaleTo3(netRevenue).doubleValue()),

                FeathercoinDailyStats.class);
        return writeResult;
    }

    public void insertDailyStats(FeathercoinDailyStats feathercoinDailyStats){
        mongoTemplate.insert(feathercoinDailyStats);
    }

    private BigDecimal scaleTo3(BigDecimal toRound){
        return toRound.setScale(3,RoundingMode.HALF_UP);
    }

    public List<DailyStats> geStats(Interval interval){
        Query query = new Query(where("fetchTime").gte(interval.getStart().toDate()).lte(interval.getEnd().toDate()));
        MapReduceResults<StatsValueObject> results = mongoTemplate.mapReduce(query, "feathercoinDailyStats",
                "classpath:/org/feathercoin/monitoring/mongodb/mapStatsFunction.js",
                "classpath:/org/feathercoin/monitoring/mongodb/reduceStatsFunction.js",
                StatsValueObject.class);

        List<DailyStats> miningDatas = new ArrayList<DailyStats>();
        for (StatsValueObject miningDataValueObject : results) {
            DailyStats dailyStats = new DailyStats();
            dailyStats.setFetchTime(miningDataValueObject.getId());
            dailyStats.setDifficulty(scaleTo3(BigDecimal.valueOf(miningDataValueObject.getValue())));

            miningDatas.add(dailyStats);
        }

        return miningDatas;
    }

    public List<DailyStats> getStatsLast30Days(){
        Interval interval = DateUtil.getIntervalForLast30Days();

        return geStats(interval);
    }
}
