package org.feathercoin.monitoring;

import org.feathercoin.monitoring.beans.PoolInfoBean;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Component
public class PoolTotalSummaryCalculator implements Serializable{
    public PoolInfoBean calculateTotal(List<PoolInfoBean> poolInfoBeans){
        BigDecimal totalPayoutHistory = BigDecimal.ZERO;
        int totalHashrate = 0;
        BigDecimal totalRoundEstimate = BigDecimal.ZERO;
        BigDecimal totalConfirmedRewards = BigDecimal.ZERO;

        for (PoolInfoBean poolInfoBean : poolInfoBeans) {
            if (poolInfoBean.isHasErrors())
                continue;
            totalPayoutHistory = totalPayoutHistory.add(poolInfoBean.getPayoutHistory());
            totalHashrate += poolInfoBean.getTotalHashrate();
            totalRoundEstimate = totalRoundEstimate.add(poolInfoBean.getRoundEstimate());
            totalConfirmedRewards = totalConfirmedRewards.add(poolInfoBean.getConfirmedRewards());
        }

        return new PoolInfoBean(totalPayoutHistory,totalHashrate,totalRoundEstimate,totalConfirmedRewards,"Total");
    }
}
