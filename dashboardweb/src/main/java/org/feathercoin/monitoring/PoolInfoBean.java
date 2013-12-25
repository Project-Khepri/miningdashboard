package org.feathercoin.monitoring;

import java.io.Serializable;
import java.math.BigDecimal;

public class PoolInfoBean implements Serializable {
    private BigDecimal payoutHistory;
    private Integer totalHashrate;
    private BigDecimal roundEstimate;
    private BigDecimal confirmedRewards;
    private String poolName;
    private boolean hasErrors = false;

    public PoolInfoBean(boolean hasErrors, String poolName){
        this.hasErrors = hasErrors;
        this.poolName = poolName;
    }

    public PoolInfoBean(BigDecimal payoutHistory, Integer totalHashrate, BigDecimal roundEstimate, BigDecimal confirmedRewards, String poolName) {
        this.payoutHistory = payoutHistory;
        this.totalHashrate = totalHashrate;
        this.roundEstimate = roundEstimate;
        this.confirmedRewards = confirmedRewards;
        this.poolName = poolName;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public BigDecimal getPayoutHistory() {
        return payoutHistory;
    }

    public Integer getTotalHashrate() {
        return totalHashrate;
    }

    public BigDecimal getRoundEstimate() {
        return roundEstimate;
    }

    public BigDecimal getConfirmedRewards() {
        return confirmedRewards;
    }

    public String getPoolName() {
        return poolName;
    }
}
