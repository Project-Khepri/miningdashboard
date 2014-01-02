package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.MiningStats;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class FeathercoinStatsServiceTest {
    @Mock private FeathercoinRepository feathercoinRepository;
    @Mock private GapFillerUtil gapFillerUtil;
    @InjectMocks private FeathercoinStatsService feathercoinStatsService;


    @Before
    public void setUp() throws Exception {
        feathercoinStatsService = new FeathercoinStatsService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentMonthMiningStats_NoData() throws Exception {
        List<MiningStats> stats = Collections.EMPTY_LIST;
        when(feathercoinRepository.getMiningStatsCurrentMonth()).thenReturn(stats);
        when(gapFillerUtil.fillGaps(eq(stats), any(DateTime.class))).thenReturn(stats);
        assertEquals(stats, feathercoinStatsService.getCurrentMonthMiningStats());
    }

}
