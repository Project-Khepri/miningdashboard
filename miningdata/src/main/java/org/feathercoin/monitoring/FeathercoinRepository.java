package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class FeathercoinRepository {
    @Autowired private MongoTemplate mongoTemplate;

    public List<MiningStats> getMiningStats(Interval interval){
        TypedAggregation<FeathercoinDailyMiningData> agg = newAggregation(FeathercoinDailyMiningData.class,
            match(where("fetchTime").gte(interval.getStart().toDate()).lte(interval.getEnd().toDate())),
            group("fetchTime").max("dailyProduction").as("dailyProduction"),
            project()
                    .and("fetchTime").previousOperation()
                    .and("dailyProduction").as("dailyProduction"),
            sort(Sort.Direction.ASC,"fetchTime")

        );

        AggregationResults<MiningStats> results = mongoTemplate.aggregate(agg, MiningStats.class);
        List<MiningStats> miningDatas = results.getMappedResults();

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
