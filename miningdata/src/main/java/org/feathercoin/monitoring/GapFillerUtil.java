package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GapFillerUtil {
    public List<MiningStats> fillGaps(List<MiningStats> miningStats, DateTime orientationDate){
        if (miningStats==null || miningStats.isEmpty())
            return Collections.EMPTY_LIST;
        ArrayList<MiningStats> result = new ArrayList<MiningStats>();

        DateTime lastFetchTime = null;

        for (int i=0;i<miningStats.size();i++){
            MiningStats miningStat = miningStats.get(i);

            if (lastFetchTime==null){
                lastFetchTime = new DateTime(miningStat.getFetchTime()).withTimeAtStartOfDay();
                result.add(miningStat);
                continue;
            }

            DateTime iterDate = new DateTime(miningStat.getFetchTime()).withTimeAtStartOfDay();
            long millisForOneDay = 1000*3600*24;
            long currentDateDistance = iterDate.minus(lastFetchTime.getMillis()).getMillis();

            if (currentDateDistance>millisForOneDay){
                int gapCounter = countGapDays(iterDate, currentDateDistance);

                BigDecimal valueForEachDay = getValueForEachGapDay(miningStat, gapCounter);

                addMiningStatForEachGapDay(result, iterDate, currentDateDistance, gapCounter, valueForEachDay);
            }else{
                result.add(miningStat);
            }

            lastFetchTime = new DateTime(miningStat.getFetchTime()).withTimeAtStartOfDay();
        }

        DateTime fillOrientationDateGapTemp = lastFetchTime;

        while(fillOrientationDateGapTemp.isBefore(orientationDate)){
            fillOrientationDateGapTemp = fillOrientationDateGapTemp.plusDays(1);
            result.add(new MiningStats(BigDecimal.ZERO.setScale(2),fillOrientationDateGapTemp.toDate()));
        }

        return result;
    }

    private void addMiningStatForEachGapDay(ArrayList<MiningStats> result, DateTime iterDate, long currentDateDistance, int gapCounter, BigDecimal valueForEachDay) {
        DateTime currentGapFillingDate = iterDate.minus(currentDateDistance).plusDays(1);
        for (int j=0;j<gapCounter;j++){
            result.add(new MiningStats(valueForEachDay,currentGapFillingDate.toDate()));
            currentGapFillingDate = currentGapFillingDate.plusDays(1);
        }
    }

    private BigDecimal getValueForEachGapDay(MiningStats miningStat, int gapCounter) {
        return miningStat.getDailyProduction().divide(
                            BigDecimal.valueOf(gapCounter), 2, RoundingMode.HALF_UP
                    );
    }

    private int countGapDays(DateTime iterDate, long currentDateDistance) {
        DateTime startDate = iterDate.minus(currentDateDistance);
        int gapCounter =0 ;
        while(startDate.isBefore(iterDate)){
            gapCounter++;
            startDate = startDate.plusDays(1);
        }
        return gapCounter;
    }
}
