package org.feathercoin.monitoring.domain;

import java.util.Date;

public class FeathercoinDailyStats {
    private String id;
    private Double price;
    private Double difficulty;
    private Double netRevenue;
    private Date fetchTime;

    public String getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getNetRevenue() {
        return netRevenue;
    }

    public void setNetRevenue(Double netRevenue) {
        this.netRevenue = netRevenue;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }
}
