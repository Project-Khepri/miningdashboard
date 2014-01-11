package org.feathercoin.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Component
public class MinerStatsConfiguration implements Serializable{
    @Value("${mining.powerConsumption:@null}")
    private Integer powerConsumptionInWatts;

    @Value("${mining.hardwareCosts:@null}")
    private BigDecimal hardwareCosts;

    @Value("${mining.electricityRate:@null}")
    private BigDecimal electricityRate;

    public Integer getPowerConsumptionInWatts() {
        return powerConsumptionInWatts;
    }

    public BigDecimal getHardwareCosts() {
        return hardwareCosts;
    }

    public BigDecimal getElectricityRate() {
        return electricityRate;
    }
}
