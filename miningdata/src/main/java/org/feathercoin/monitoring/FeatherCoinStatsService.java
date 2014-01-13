package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.DailyStats;
import org.feathercoin.monitoring.domain.FeathercoinDailyStats;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FeathercoinStatsService implements Serializable{
    @Autowired FeathercoinStatsRepository feathercoinStatsRepository;
    @Autowired GapFillerUtil gapFillerUtil;


    public List<DailyStats> getLast30DaysStats(){
        List<DailyStats> statsLast30Days = feathercoinStatsRepository.getStatsLast30Days();
        //TODO: fill gaps
        //statsLast30Days = gapFillerUtil.fillGaps(miningStatsLastMonth,new DateTime().withTimeAtStartOfDay());
        return statsLast30Days;
    }

    public void insertNewEntryForToday(BigDecimal difficulty,BigDecimal netRevenue, BigDecimal price){
        DateTime today = getToday();
        FeathercoinDailyStats feathercoinDailyStats = new FeathercoinDailyStats();
        feathercoinDailyStats.setFetchTime(today.toDate());
        feathercoinDailyStats.setDifficulty(difficulty.doubleValue());
        feathercoinDailyStats.setNetRevenue(netRevenue.doubleValue());
        feathercoinDailyStats.setPrice(price.doubleValue());
        feathercoinStatsRepository.insertDailyStats(feathercoinDailyStats);
    }

    /**
     * for testing purpose to ensure the day fetched for today
     */
    protected DateTime getToday(){
        return new DateTime().withTimeAtStartOfDay();
    }


}
