package org.feathercoin.monitoring.dto;


import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Summary {
    @SerializedName("MHS 5s") private BigDecimal mhs5s;
    @SerializedName("Getworks") private Integer getworks;
    @SerializedName("Accepted") private Integer accepted;
    @SerializedName("Rejected") private Integer rejected;
    @SerializedName("Hardware Errors") private Integer hardwareErrors;
    @SerializedName("Utility") private BigDecimal utility;
    @SerializedName("Discarded") private Integer discarded;
    @SerializedName("Stale") private Integer stale;
    @SerializedName("Get Failures") private Integer getFailures;
    @SerializedName("Local Work") private Integer localWork;
    @SerializedName("Remote Failures") private Integer remoteFailures;
    @SerializedName("Network Blocks") private Integer networkBlocks;
    @SerializedName("Total MH") private BigDecimal totalMH;
    @SerializedName("Work Utility") private BigDecimal workUtility;
    @SerializedName("Difficulty Accepted") private BigDecimal difficultyAccepted;
    @SerializedName("Difficulty Rejected") private BigDecimal difficultyRejected;
    @SerializedName("Difficulty Stale") private BigDecimal difficultyStale;
    @SerializedName("Best Share") private Integer bestShare;
    @SerializedName("Device Hardware%") private BigDecimal deviceHardwarePercent;
    @SerializedName("Device Rejected%") private BigDecimal deviceRejectedPercent;
    @SerializedName("Pool Rejected%") private BigDecimal poolRejectedPercent;
    @SerializedName("Pool Stale%") private BigDecimal poolStalePercent;
    @SerializedName("MHS av") private BigDecimal mhsAv;
    @SerializedName("Elapsed") private Integer elapsed;
    @SerializedName("Found Blocks") private Integer foundBlocks;

    public BigDecimal getMhs5s() {
        return mhs5s;
    }

    public Integer getGetworks() {
        return getworks;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public Integer getRejected() {
        return rejected;
    }

    public Integer getHardwareErrors() {
        return hardwareErrors;
    }

    public BigDecimal getUtility() {
        return utility;
    }

    public Integer getDiscarded() {
        return discarded;
    }

    public Integer getStale() {
        return stale;
    }

    public Integer getGetFailures() {
        return getFailures;
    }

    public Integer getLocalWork() {
        return localWork;
    }

    public Integer getRemoteFailures() {
        return remoteFailures;
    }

    public Integer getNetworkBlocks() {
        return networkBlocks;
    }

    public BigDecimal getTotalMH() {
        return totalMH;
    }

    public BigDecimal getWorkUtility() {
        return workUtility;
    }

    public BigDecimal getDifficultyAccepted() {
        return difficultyAccepted;
    }

    public BigDecimal getDifficultyRejected() {
        return difficultyRejected;
    }

    public BigDecimal getDifficultyStale() {
        return difficultyStale;
    }

    public Integer getBestShare() {
        return bestShare;
    }

    public BigDecimal getDeviceHardwarePercent() {
        return deviceHardwarePercent;
    }

    public BigDecimal getDeviceRejectedPercent() {
        return deviceRejectedPercent;
    }

    public BigDecimal getPoolRejectedPercent() {
        return poolRejectedPercent;
    }

    public BigDecimal getPoolStalePercent() {
        return poolStalePercent;
    }

    public BigDecimal getMhsAv() {
        return mhsAv;
    }

    public Integer getElapsed() {
        return elapsed;
    }

    public Integer getFoundBlocks() {
        return foundBlocks;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "mhs5s=" + mhs5s +
                ", getworks=" + getworks +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", hardwareErrors=" + hardwareErrors +
                ", utility=" + utility +
                ", discarded=" + discarded +
                ", stale=" + stale +
                ", getFailures=" + getFailures +
                ", localWork=" + localWork +
                ", remoteFailures=" + remoteFailures +
                ", networkBlocks=" + networkBlocks +
                ", totalMH=" + totalMH +
                ", workUtility=" + workUtility +
                ", difficultyAccepted=" + difficultyAccepted +
                ", difficultyRejected=" + difficultyRejected +
                ", difficultyStale=" + difficultyStale +
                ", bestShare=" + bestShare +
                ", deviceHardwarePercent=" + deviceHardwarePercent +
                ", deviceRejectedPercent=" + deviceRejectedPercent +
                ", poolRejectedPercent=" + poolRejectedPercent +
                ", poolStalePercent=" + poolStalePercent +
                ", mhsAv=" + mhsAv +
                ", elapsed=" + elapsed +
                ", foundBlocks=" + foundBlocks +
                '}';
    }
}
