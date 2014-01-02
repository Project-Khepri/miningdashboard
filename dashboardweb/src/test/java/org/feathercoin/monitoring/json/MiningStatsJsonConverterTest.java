package org.feathercoin.monitoring.json;

import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MiningStatsJsonConverterTest {
    @Test
    public void testTransformToJson() throws Exception {
        List<MiningStats> miningStatsTestData = createMiningStatsTestData();
        MiningStatsJsonConverter miningStatsJsonConverter = new MiningStatsJsonConverter();
        String result = miningStatsJsonConverter.transformToJson(miningStatsTestData);
        System.out.println(result);

    }

    private List<MiningStats> createMiningStatsTestData() {
        ArrayList<MiningStats> miningStatsExpected = new ArrayList<MiningStats>();
        miningStatsExpected.add(new MiningStats(BigDecimal.ONE, getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStatsExpected.add(new MiningStats(BigDecimal.valueOf(80), getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 3).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 4).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 5).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 6).toDate()));
        return miningStatsExpected;
    }

    private BigDecimal createScaledBigDecimal(int val) {
        return BigDecimal.valueOf(val).setScale(2);
    }

    private DateTime getDateTimeWithStartOfDay(int year, int monthOfYear, int dayOfMonth) {
        return new DateTime().withDate(year, monthOfYear, dayOfMonth).withTimeAtStartOfDay();
    }
}
