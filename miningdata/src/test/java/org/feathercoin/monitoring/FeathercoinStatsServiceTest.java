package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeathercoinStatsServiceTest {
    @Mock private FeathercoinRepository feathercoinRepository;
    @Mock private GapFillerUtil gapFillerUtil;
    @InjectMocks private FeathercoinStatsService feathercoinStatsService;


    @Before
    public void setUp() throws Exception {
        feathercoinStatsService = spy(new FeathercoinStatsService());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentMonthMiningStats_NoData() throws Exception {
        List<MiningStats> stats = Collections.EMPTY_LIST;
        when(feathercoinRepository.getMiningStatsCurrentMonth()).thenReturn(stats);
        when(gapFillerUtil.fillGaps(eq(stats), any(DateTime.class))).thenReturn(stats);
        assertEquals(stats, feathercoinStatsService.getCurrentMonthMiningStats());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateDailyProduction_NullArg(){
        feathercoinStatsService.updateDailyProduction(null);
    }

    @Test(expected = CorruptedDateEntryException.class)
    public void testUpdateDailyProduction_EntryYoungerThanToday(){
        when(feathercoinRepository.getLatestMiningDataEntry()).thenReturn(
                new FeathercoinDailyMiningData(BigDecimal.TEN,BigDecimal.TEN,new DateTime().plusDays(2).toDate()));
        feathercoinStatsService.updateDailyProduction(BigDecimal.TEN);
    }

    @Test
    public void testUpdateDailyProduction_UpdateRecordBasedOnTodayEntry(){
        DateTime today = new DateTime().withTimeAtStartOfDay();
        when(feathercoinStatsService.getToday()).thenReturn(today);

        FeathercoinDailyMiningData feathercoinDailyMiningData = new FeathercoinDailyMiningData(BigDecimal.TEN, BigDecimal.valueOf(100),
                today.toDate());
        feathercoinDailyMiningData = spy(feathercoinDailyMiningData);
        when(feathercoinDailyMiningData.getId()).thenReturn("10000");
        when(feathercoinRepository.getLatestMiningDataEntry()).thenReturn(feathercoinDailyMiningData);

        BigDecimal result = feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(150));
        assertEquals(BigDecimal.valueOf(60).setScale(1),result);

        verify(feathercoinRepository).updateDailyProductionAndTotalCoinsMined(eq("10000"),
                eq(BigDecimal.valueOf(60).setScale(1)), eq(BigDecimal.valueOf(150)));
    }


    @Test
    public void testUpdateDailyProduction_UpdateRecordBasedOnTodayEntry_NoTotalCoinsData(){
        DateTime today = new DateTime().withTimeAtStartOfDay();
        when(feathercoinStatsService.getToday()).thenReturn(today);

        FeathercoinDailyMiningData feathercoinDailyMiningData = new FeathercoinDailyMiningData(BigDecimal.TEN, BigDecimal.ONE,
                today.toDate());
        feathercoinDailyMiningData.setTotalCoinsMined(null);
        feathercoinDailyMiningData = spy(feathercoinDailyMiningData);
        when(feathercoinDailyMiningData.getId()).thenReturn("10000");
        when(feathercoinRepository.getLatestMiningDataEntry()).thenReturn(feathercoinDailyMiningData);

        BigDecimal result = feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(150));
        assertEquals(BigDecimal.ZERO,result);

        verify(feathercoinRepository).updateDailyProductionAndTotalCoinsMined(eq("10000"),
                eq(BigDecimal.valueOf(0)), eq(BigDecimal.valueOf(150)));
    }

    @Test
    public void testUpdateDailyProduction_CreateRecordBasedOnPastEntry(){
        FeathercoinDailyMiningData feathercoinDailyMiningData = new FeathercoinDailyMiningData(BigDecimal.TEN, BigDecimal.valueOf(100),
                new DateTime().minusDays(2).toDate());
        feathercoinDailyMiningData = spy(feathercoinDailyMiningData);
        when(feathercoinDailyMiningData.getId()).thenReturn("10000");
        when(feathercoinRepository.getLatestMiningDataEntry()).thenReturn(feathercoinDailyMiningData);

        BigDecimal result = feathercoinStatsService.updateDailyProduction(BigDecimal.valueOf(150));
        assertEquals(BigDecimal.valueOf(50).setScale(1),result);

        checkInsertDailyProductionEntry(50.0, 150.0);
    }

    private void checkInsertDailyProductionEntry(double expectedDailyProduction, double expectedTotalCoinsMined) {
        ArgumentCaptor<FeathercoinDailyMiningData> argument = ArgumentCaptor.forClass(FeathercoinDailyMiningData.class);
        verify(feathercoinRepository).insertDailyProductionEntry(argument.capture());

        assertEquals(expectedDailyProduction,argument.getValue().getDailyProduction(),0.001);
        assertEquals(expectedTotalCoinsMined, argument.getValue().getTotalCoinsMined(), 0.001);
        assertNotNull(argument.getValue().getFetchTime());
    }

    @Test
    public void testUpdateDailyProduction_NewRecordCreation(){
        BigDecimal result = feathercoinStatsService.updateDailyProduction(BigDecimal.TEN);
        assertEquals(BigDecimal.ZERO.setScale(1), result);

        checkInsertDailyProductionEntry(0.0, 10.0);
    }

}
