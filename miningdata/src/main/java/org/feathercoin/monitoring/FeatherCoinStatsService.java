package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeathercoinStatsService {
    @Autowired FeathercoinRepository feathercoinRepository;
    @Autowired GapFillerUtil gapFillerUtil;

    public List<MiningStats> getCurrentMonthMiningStats(){
        List<MiningStats> miningStatsLastMonth = feathercoinRepository.getMiningStatsCurrentMonth();
        miningStatsLastMonth = gapFillerUtil.fillGaps(miningStatsLastMonth,new DateTime().withTimeAtStartOfDay());
        return miningStatsLastMonth;
    }

    public List<MiningStats> getLast30DaysMiningStats(){
        List<MiningStats> miningStatsLastMonth = feathercoinRepository.getMiningStatsLast30Days();
        miningStatsLastMonth = gapFillerUtil.fillGaps(miningStatsLastMonth,new DateTime().withTimeAtStartOfDay());
        return miningStatsLastMonth;
    }
}
