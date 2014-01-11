package org.feathercoin.monitoring;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ProfitabilityCalculatorTest {
    ProfitabilityCalculator profitabilityCalculator;
    @Before
    public void setUp() throws Exception {
        profitabilityCalculator = new ProfitabilityCalculator();
    }

    @Test
    public void testCalculateCoinsPerDay() throws Exception {
        profitabilityCalculator.setFeathercoinsPerBlock(BigDecimal.valueOf(200));
        profitabilityCalculator.setDifficulty(BigDecimal.valueOf(228.13690695));
        profitabilityCalculator.setHashRate(BigDecimal.valueOf(500));
        assertEquals(new BigDecimal("8.81776117710898"), profitabilityCalculator.calculateCoinsPerDay());
    }

    @Test
    public void calculatePowerCostsPerDay() throws Exception{
        profitabilityCalculator.setElectricityRate(BigDecimal.valueOf(0.15));
        profitabilityCalculator.setPowerConsumptionInWatts(BigDecimal.valueOf(400.00));
        assertEquals(new BigDecimal("1.44"), profitabilityCalculator.calculatePowerCostsPerDay());
    }



    @Test
    public void testCalculateRevenuePerDay() throws Exception {
        profitabilityCalculator.setFeathercoinsPerBlock(BigDecimal.valueOf(200));
        profitabilityCalculator.setDifficulty(BigDecimal.valueOf(228.13690695));
        profitabilityCalculator.setHashRate(BigDecimal.valueOf(500));
        profitabilityCalculator.setConversionRate(BigDecimal.valueOf(0.35549261));
        assertEquals(new BigDecimal("3.13"), profitabilityCalculator.calculateRevenuePerDay());
    }

    @Test
    public void testCalculateNetRevenuePerDay() throws Exception {
        profitabilityCalculator.setFeathercoinsPerBlock(BigDecimal.valueOf(200));
        profitabilityCalculator.setDifficulty(BigDecimal.valueOf(228.13690695));
        profitabilityCalculator.setHashRate(BigDecimal.valueOf(500));
        profitabilityCalculator.setConversionRate(BigDecimal.valueOf(0.35549261));
        profitabilityCalculator.setElectricityRate(BigDecimal.valueOf(0.15));
        profitabilityCalculator.setPowerConsumptionInWatts(BigDecimal.valueOf(400.00));
        assertEquals(new BigDecimal("3.13").subtract(new BigDecimal("1.44")), profitabilityCalculator.calculateNetRevenuePerDay());
    }

    @Test
    public void testCalculateHardwareBreakEvenInDays() throws Exception {
        profitabilityCalculator.setFeathercoinsPerBlock(BigDecimal.valueOf(200));
        profitabilityCalculator.setDifficulty(BigDecimal.valueOf(228.13690695));
        profitabilityCalculator.setHashRate(BigDecimal.valueOf(500));
        profitabilityCalculator.setConversionRate(BigDecimal.valueOf(0.35549261));
        profitabilityCalculator.setElectricityRate(BigDecimal.valueOf(0.15));
        profitabilityCalculator.setPowerConsumptionInWatts(BigDecimal.valueOf(400.00));
        profitabilityCalculator.setMiningHardwareCosts(BigDecimal.valueOf(1199.00));
        assertEquals(new BigDecimal("709"), profitabilityCalculator.calculateHardwareBreakEvenInDays());
    }

    @Test
    public void testCalculateRealWorldExample(){
        profitabilityCalculator.setElectricityRate(BigDecimal.valueOf(0.35));
        profitabilityCalculator.setMiningHardwareCosts(BigDecimal.valueOf(1500));
        profitabilityCalculator.setConversionRate(BigDecimal.valueOf(0.346924));
        profitabilityCalculator.setDifficulty(BigDecimal.valueOf(225.77899794));
        profitabilityCalculator.setPowerConsumptionInWatts(BigDecimal.valueOf(2000));
        profitabilityCalculator.setFeathercoinsPerBlock(BigDecimal.valueOf(200));
        profitabilityCalculator.setHashRate(BigDecimal.valueOf(4500));

        assertEquals(new BigDecimal("80.18864028857202"), profitabilityCalculator.calculateCoinsPerDay());
        assertEquals(new BigDecimal("16.80"), profitabilityCalculator.calculatePowerCostsPerDay());
        assertEquals(new BigDecimal("27.82"), profitabilityCalculator.calculateRevenuePerDay());
        assertEquals(new BigDecimal("11.02"), profitabilityCalculator.calculateNetRevenuePerDay());
        assertEquals(new BigDecimal("136"), profitabilityCalculator.calculateHardwareBreakEvenInDays());

    }

}
