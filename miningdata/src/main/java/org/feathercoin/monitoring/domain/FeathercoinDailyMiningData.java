package org.feathercoin.monitoring.domain;

import java.math.BigDecimal;
import java.util.Date;

public class FeathercoinDailyMiningData {
    private String id;

    private double dailyProduction;
    private Date fetchTime;

    public FeathercoinDailyMiningData() {
    }

    public FeathercoinDailyMiningData(BigDecimal dailyProduction, Date fetchTime) {
        this.dailyProduction = dailyProduction.doubleValue();
        this.fetchTime = fetchTime;
    }


    @Override
    public String toString() {

        return "FeathercoinDailyMiningData{" +
                "id='" + id + '\'' +
                ", dailyProduction=" + dailyProduction +
                ", fetchTime=" + fetchTime +
                '}';
    }
}
