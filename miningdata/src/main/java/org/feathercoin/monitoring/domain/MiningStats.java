package org.feathercoin.monitoring.domain;

import java.math.BigDecimal;
import java.util.Date;

public class MiningStats {
    private BigDecimal dailyProduction;
    private Date fetchTime;

    public MiningStats() {
    }

    public MiningStats(BigDecimal dailyProduction, Date fetchTime) {
        this.dailyProduction = dailyProduction;
        this.fetchTime = fetchTime;
    }

    public BigDecimal getDailyProduction() {
        return dailyProduction;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    @Override
    public String toString() {
        return "MiningStats{" +
                "dailyProduction=" + dailyProduction +
                ", fetchTime=" + fetchTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiningStats that = (MiningStats) o;

        if (!dailyProduction.equals(that.dailyProduction)) return false;
        if (!fetchTime.equals(that.fetchTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dailyProduction.hashCode();
        result = 31 * result + fetchTime.hashCode();
        return result;
    }
}
