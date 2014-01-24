package org.feathercoin.monitoring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Mining p
 */
@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ProfitabilityCalculator implements Serializable{
    private BigDecimal difficulty;
    private BigDecimal hashRate;
    private BigDecimal feathercoinsPerBlock;
    private BigDecimal powerConsumptionInWatts;
    private BigDecimal electricityRate;
    private BigDecimal conversionRate;
    private BigDecimal miningHardwareCosts;


    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public void setHashRate(BigDecimal hashRate) {
        this.hashRate = hashRate;
    }

    public void setFeathercoinsPerBlock(BigDecimal feathercoinsPerBlock) {
        this.feathercoinsPerBlock = feathercoinsPerBlock;
    }

    public void setPowerConsumptionInWatts(BigDecimal powerConsumptionInWatts) {
        this.powerConsumptionInWatts = powerConsumptionInWatts;
    }

    public void setElectricityRate(BigDecimal electricityRate) {
        this.electricityRate = electricityRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    public void setMiningHardwareCosts(BigDecimal miningHardwareCosts) {
        this.miningHardwareCosts = miningHardwareCosts;
    }

    private BigDecimal calculateHashtime(){
        if (BigDecimal.ZERO.equals(hashRate))
            return BigDecimal.ZERO;
        return difficulty.multiply(BigDecimal.valueOf(2).pow(32,MathContext.UNLIMITED)).divide(hashRate.multiply(BigDecimal.valueOf(1000)),20,RoundingMode.FLOOR);
    }

    public BigDecimal calculateCoinsPerDay(){

        BigDecimal hashtime = calculateHashtime();
        if (BigDecimal.ZERO.equals(hashtime))
            return BigDecimal.ZERO;
        return BigDecimal.valueOf(3600).multiply(BigDecimal.valueOf(24).multiply(feathercoinsPerBlock)).divide(hashtime,14, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePowerCostsPerDay(){
        return powerConsumptionInWatts.divide(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(24)).multiply(electricityRate).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal calculateRevenuePerDay(){
        return calculateCoinsPerDay().multiply(conversionRate).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal calculateNetRevenuePerDay(){
        return calculateRevenuePerDay().subtract(calculatePowerCostsPerDay());
    }

    public BigDecimal calculateHardwareBreakEvenInDays(){

        BigDecimal netRevenuePerDay = calculateNetRevenuePerDay();
        if (BigDecimal.ZERO.equals(netRevenuePerDay))
            return BigDecimal.ZERO;
        return miningHardwareCosts.divide(netRevenuePerDay,0,RoundingMode.FLOOR);
    }
}
