package org.feathercoin.monitoring.dto;


import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class D2Response {
    @SerializedName("username") private String username="";
    @SerializedName("confirmed_rewards") private BigDecimal confirmedRewards=BigDecimal.ZERO;
    @SerializedName("round_estimate") private BigDecimal roundEstimate=BigDecimal.ZERO;
    @SerializedName("total_hashrate") private Integer totalHashrate=0;
    @SerializedName("payout_history") private BigDecimal payoutHistory=BigDecimal.ZERO;
    @SerializedName("round_shares") private Integer roundShares=0;
    @SerializedName("workers") private HashMap<String,Worker> workers = new HashMap<String, Worker>();

    public static final D2Response EMPTY_RESPONSE = new D2Response();


    public String getUsername() {
        return username;
    }

    public BigDecimal getConfirmedRewards() {
        return confirmedRewards;
    }

    public BigDecimal getRoundEstimate() {
        return roundEstimate;
    }

    public Integer getTotalHashrate() {
        return totalHashrate;
    }

    public BigDecimal getPayoutHistory() {
        return payoutHistory;
    }

    public Integer getRoundShares() {
        return roundShares;
    }

    public Map<String,Worker> getWorkers(){
        return workers;
    }

    public Worker getWorkerByName(String workerName){
        return workers.get(workerName);
    }


    @Override
    public String toString() {
        return "D2Response{" +
                "username='" + username + '\'' +
                ", confirmedRewards=" + confirmedRewards +
                ", roundEstimate=" + roundEstimate +
                ", totalHashrate=" + totalHashrate +
                ", payoutHistory=" + payoutHistory +
                ", roundShares=" + roundShares +
                ", workers=" + workers +
                '}';
    }
}
