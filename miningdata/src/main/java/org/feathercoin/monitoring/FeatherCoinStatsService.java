package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class FeathercoinStatsService implements Serializable{
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

    public BigDecimal updateDailyProduction(BigDecimal totalPayoutHistory){
        if (totalPayoutHistory==null)
            throw new IllegalArgumentException("null not allowed");

        DateTime today = getToday();
        FeathercoinDailyMiningData latestMiningDataEntry = feathercoinRepository.getLatestMiningDataEntry();
        if (latestMiningDataEntry!=null){
            return updateDailyProductionBasedOnPastEntries(totalPayoutHistory, today, latestMiningDataEntry);
        }else{
            return insertNewEntryForToday(totalPayoutHistory, today);
        }
    }

    /**
     * for testing purpose to ensure the day fetched for today
     */
    protected DateTime getToday(){
        return new DateTime().withTimeAtStartOfDay();
    }

    private BigDecimal updateDailyProductionBasedOnPastEntries(BigDecimal totalPayoutHistory, DateTime today, FeathercoinDailyMiningData latestMiningDataEntry) {
        DateTime lastEntryDate = new DateTime(latestMiningDataEntry.getFetchTime()).withTimeAtStartOfDay();
        if (today.isBefore(lastEntryDate))
            throw new CorruptedDateEntryException("cannot update daily production as an entry in the future was found");

        if (today.isEqual(lastEntryDate)){
            return updateExistingEntryForToday(totalPayoutHistory, latestMiningDataEntry);
        }else{
            return insertNewEntryForTodayBasedOnPastEntry(totalPayoutHistory, today, latestMiningDataEntry);
        }
    }

    private BigDecimal insertNewEntryForToday(BigDecimal totalPayoutHistory, DateTime today) {
        FeathercoinDailyMiningData defaultMiningDataEntry = createDefaultMiningDataEntry(totalPayoutHistory, today);
        feathercoinRepository.insertDailyProductionEntry(defaultMiningDataEntry);
        return BigDecimal.valueOf(defaultMiningDataEntry.getDailyProduction());
    }

    private BigDecimal insertNewEntryForTodayBasedOnPastEntry(BigDecimal totalPayoutHistory, DateTime today, FeathercoinDailyMiningData latestMiningDataEntry) {
        FeathercoinDailyMiningData feathercoinDailyMiningData = createDefaultMiningDataEntry(totalPayoutHistory, today);

        if (latestMiningDataEntry.getTotalCoinsMined()!=null){
            BigDecimal newDailyProduction = totalPayoutHistory.subtract(BigDecimal.valueOf(latestMiningDataEntry.getTotalCoinsMined()));
            if (newDailyProduction.compareTo(BigDecimal.ZERO)==1){
                feathercoinDailyMiningData.setDailyProduction(newDailyProduction.doubleValue());
            }
        }
        feathercoinRepository.insertDailyProductionEntry(feathercoinDailyMiningData);
        return BigDecimal.valueOf(feathercoinDailyMiningData.getDailyProduction());
    }

    private BigDecimal updateExistingEntryForToday(BigDecimal totalPayoutHistory, FeathercoinDailyMiningData latestMiningDataEntry) {
        if (latestMiningDataEntry.getTotalCoinsMined()==null){
            feathercoinRepository.updateDailyProductionAndTotalCoinsMined(
                    latestMiningDataEntry.getId(),BigDecimal.ZERO,totalPayoutHistory);
            return BigDecimal.ZERO;
        }else{
            BigDecimal newDailyProduction = totalPayoutHistory.subtract(BigDecimal.valueOf(latestMiningDataEntry.getTotalCoinsMined()));
            newDailyProduction = newDailyProduction.add(BigDecimal.valueOf(latestMiningDataEntry.getDailyProduction()));
            if (newDailyProduction.compareTo(BigDecimal.ZERO)==1){
                feathercoinRepository.updateDailyProductionAndTotalCoinsMined(
                        latestMiningDataEntry.getId(),newDailyProduction,totalPayoutHistory);
            }
            return newDailyProduction;
        }
    }

    private BigDecimal scaleTo3(BigDecimal toRound){
        return toRound.setScale(3, RoundingMode.HALF_UP);
    }

    private FeathercoinDailyMiningData createDefaultMiningDataEntry(BigDecimal totalPayoutHistory, DateTime today) {
        FeathercoinDailyMiningData feathercoinDailyMiningData = new FeathercoinDailyMiningData();
        feathercoinDailyMiningData.setFetchTime(today.toDate());
        feathercoinDailyMiningData.setTotalCoinsMined(totalPayoutHistory.doubleValue());
        feathercoinDailyMiningData.setDailyProduction(0.0);
        return feathercoinDailyMiningData;
    }
}
