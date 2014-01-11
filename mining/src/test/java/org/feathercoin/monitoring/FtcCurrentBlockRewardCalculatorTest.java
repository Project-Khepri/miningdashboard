package org.feathercoin.monitoring;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class FtcCurrentBlockRewardCalculatorTest {
    @Test
    public void testCalculateCurrentBlockReward() throws Exception {
        FtcCurrentBlockRewardCalculator ftcCurrentBlockRewardCalculator = new FtcCurrentBlockRewardCalculator();
        Object[][] expectedValues = {
                {BigDecimal.valueOf(200.0000000000),0},
                {BigDecimal.valueOf(200.0000000000),10000},
                {BigDecimal.valueOf(200.0000000000),100000},
                {BigDecimal.valueOf(200.0000000000),123422},
                {BigDecimal.valueOf(100.0000000000),840000},
                {BigDecimal.valueOf(100.0000000000),850000},
                {BigDecimal.valueOf(50.0000000000),840000*2},
                {BigDecimal.valueOf(25.0000000000),840000*3},
                {BigDecimal.valueOf(12.5000000000),840000*4},
                {BigDecimal.valueOf(12.5000000000).divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP),840000*5},
        };

        for (Object[] expectedValue : expectedValues) {
            assertEquals(""+expectedValue[1],((BigDecimal)expectedValue[0]).doubleValue(),ftcCurrentBlockRewardCalculator.calculateCurrentBlockReward((Integer)expectedValue[1]).doubleValue(),0.001);
        }

    }
}
