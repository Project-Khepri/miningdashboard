package org.feathercoin.monitoring.domain;

import java.math.BigDecimal;
import java.util.Date;

public class FeathercoinDailyMiningData {
    private String id;

    private Double dailyProduction;
    private Double totalCoinsMined;
    private Date fetchTime;

    public String getId() {
        return id;
    }

    public Double getDailyProduction() {
        return dailyProduction;
    }

    public Double getTotalCoinsMined() {
        return totalCoinsMined;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public FeathercoinDailyMiningData() {
    }

    public void setDailyProduction(Double dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    public void setTotalCoinsMined(Double totalCoinsMined) {
        this.totalCoinsMined = totalCoinsMined;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public FeathercoinDailyMiningData(BigDecimal dailyProduction, Date fetchTime) {
        this.dailyProduction = dailyProduction.doubleValue();
        this.fetchTime = fetchTime;
    }

    public FeathercoinDailyMiningData(BigDecimal dailyProduction, BigDecimal totalCoinsMined, Date fetchTime) {
        this.dailyProduction = dailyProduction.doubleValue();
        this.totalCoinsMined = totalCoinsMined.doubleValue();
        this.fetchTime = fetchTime;
    }

    @Override
    public String toString() {
        return "FeathercoinDailyMiningData{" +
                "id='" + id + '\'' +
                ", dailyProduction=" + dailyProduction +
                ", totalCoinsMined=" + totalCoinsMined +
                ", fetchTime=" + fetchTime +
                '}';
    }
}
