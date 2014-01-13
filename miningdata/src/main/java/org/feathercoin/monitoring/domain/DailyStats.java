package org.feathercoin.monitoring.domain;

import java.math.BigDecimal;
import java.util.Date;

public class DailyStats {
    private BigDecimal price;
    private BigDecimal difficulty;
    private BigDecimal netRevenue;
    private Date fetchTime;

    public DailyStats() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public BigDecimal getNetRevenue() {
        return netRevenue;
    }

    public void setNetRevenue(BigDecimal netRevenue) {
        this.netRevenue = netRevenue;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyStats that = (DailyStats) o;

        if (!difficulty.equals(that.difficulty)) return false;
        if (!fetchTime.equals(that.fetchTime)) return false;
        if (!netRevenue.equals(that.netRevenue)) return false;
        if (!price.equals(that.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = price.hashCode();
        result = 31 * result + difficulty.hashCode();
        result = 31 * result + netRevenue.hashCode();
        result = 31 * result + fetchTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DailyStats{" +
                "price=" + price +
                ", difficulty=" + difficulty +
                ", netRevenue=" + netRevenue +
                ", fetchTime=" + fetchTime +
                '}';
    }
}
