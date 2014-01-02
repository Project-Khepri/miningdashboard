package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GapFillerUtilTest {
    private GapFillerUtil gapFillerUtil;

    @Before
    public void setUp(){
        gapFillerUtil = new GapFillerUtil();

    }

    @Test
    public void testFillGaps_EmptyAndNullArgs() throws Exception {
        List<MiningStats> stats = gapFillerUtil.fillGaps(Collections.EMPTY_LIST,new DateTime());
        assertTrue(stats.isEmpty());
        assertTrue(gapFillerUtil.fillGaps(null, new DateTime()).isEmpty());
    }

    @Test
    public void testFillGaps_NothingToChange() {
        ArrayList<MiningStats> miningStats = new ArrayList<MiningStats>();
        miningStats.add(new MiningStats(BigDecimal.ONE,getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStats.add(new MiningStats(BigDecimal.TEN,getDateTimeWithStartOfDay(2013, 12, 2).toDate()));

        List<MiningStats> miningStatsResult = gapFillerUtil.fillGaps(miningStats, getDateTimeWithStartOfDay(2013, 12, 2));
        assertEquals(miningStats.size(),miningStatsResult.size());

        for (int i = 0; i < miningStatsResult.size(); i++) {
            assertEquals(miningStats.get(i), miningStatsResult.get(i));
        }
    }

    @Test
    public void testFillGaps_FillOneDayGap() {
        ArrayList<MiningStats> miningStats = new ArrayList<MiningStats>();
        miningStats.add(new MiningStats(BigDecimal.ONE,getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(80),getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(100),getDateTimeWithStartOfDay(2013, 12, 4).toDate()));

        List<MiningStats> miningStatsResult = gapFillerUtil.fillGaps(miningStats, getDateTimeWithStartOfDay(2013, 12, 4));

        ArrayList<MiningStats> miningStatsExpected = new ArrayList<MiningStats>();
        miningStatsExpected.add(new MiningStats(BigDecimal.ONE, getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStatsExpected.add(new MiningStats(BigDecimal.valueOf(80), getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(50), getDateTimeWithStartOfDay(2013, 12, 3).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(50), getDateTimeWithStartOfDay(2013, 12, 4).toDate()));

        assertEquals(miningStatsExpected.size(), miningStatsResult.size());

        for (int i = 0; i < miningStatsResult.size(); i++) {
            assertEquals(miningStatsExpected.get(i).toString(),miningStatsExpected.get(i), miningStatsResult.get(i));
        }
    }

    @Test
    public void testFillGaps_FillOneDayGapAndAlignToDateOrientation() {
        ArrayList<MiningStats> miningStats = new ArrayList<MiningStats>();
        miningStats.add(new MiningStats(BigDecimal.ONE,getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(80),getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(100),getDateTimeWithStartOfDay(2013, 12, 4).toDate()));

        List<MiningStats> miningStatsResult = gapFillerUtil.fillGaps(miningStats, getDateTimeWithStartOfDay(2013, 12, 6));

        ArrayList<MiningStats> miningStatsExpected = new ArrayList<MiningStats>();
        miningStatsExpected.add(new MiningStats(BigDecimal.ONE, getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStatsExpected.add(new MiningStats(BigDecimal.valueOf(80), getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(50), getDateTimeWithStartOfDay(2013, 12, 3).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(50), getDateTimeWithStartOfDay(2013, 12, 4).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(0), getDateTimeWithStartOfDay(2013, 12, 5).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(0), getDateTimeWithStartOfDay(2013, 12, 6).toDate()));

        assertEquals(miningStatsExpected.size(), miningStatsResult.size());

        for (int i = 0; i < miningStatsResult.size(); i++) {
            assertEquals(miningStatsExpected.get(i).toString(),miningStatsExpected.get(i), miningStatsResult.get(i));
        }
    }

    @Test
    public void testFillGaps_Fill3DayGap() {
        ArrayList<MiningStats> miningStats = new ArrayList<MiningStats>();
        miningStats.add(new MiningStats(BigDecimal.ONE,getDateTimeWithStartOfDay(2013,12,1).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(80),getDateTimeWithStartOfDay(2013,12,2).toDate()));
        miningStats.add(new MiningStats(BigDecimal.valueOf(100),getDateTimeWithStartOfDay(2013,12,6).toDate()));

        List<MiningStats> miningStatsResult = gapFillerUtil.fillGaps(miningStats, getDateTimeWithStartOfDay(2013, 12, 6));

        ArrayList<MiningStats> miningStatsExpected = new ArrayList<MiningStats>();
        miningStatsExpected.add(new MiningStats(BigDecimal.ONE, getDateTimeWithStartOfDay(2013, 12, 1).toDate()));
        miningStatsExpected.add(new MiningStats(BigDecimal.valueOf(80), getDateTimeWithStartOfDay(2013, 12, 2).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 3).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 4).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 5).toDate()));
        miningStatsExpected.add(new MiningStats(createScaledBigDecimal(25), getDateTimeWithStartOfDay(2013, 12, 6).toDate()));

        assertEquals(miningStatsExpected.size(), miningStatsResult.size());

        for (int i = 0; i < miningStatsResult.size(); i++) {
            assertEquals(miningStatsExpected.get(i).toString(),miningStatsExpected.get(i), miningStatsResult.get(i));
        }
    }

    private BigDecimal createScaledBigDecimal(int val) {
        return BigDecimal.valueOf(val).setScale(2);
    }

    private DateTime getDateTimeWithStartOfDay(int year, int monthOfYear, int dayOfMonth) {
        return new DateTime().withDate(year, monthOfYear, dayOfMonth).withTimeAtStartOfDay();
    }
}
