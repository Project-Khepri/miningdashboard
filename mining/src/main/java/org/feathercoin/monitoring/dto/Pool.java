package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Pool {
    @SerializedName("POOL") private Integer pooL;
    @SerializedName("URL") private String url;
    @SerializedName("Status") private String status;
    @SerializedName("Priority") private Integer priority;
    @SerializedName("Quota") private Integer quota;
    @SerializedName("Long Poll") private String longPoll;
    @SerializedName("Getworks") private Integer getworks;
    @SerializedName("Accepted") private Integer accepted;
    @SerializedName("Rejected") private Integer rejected;
    @SerializedName("Discarded") private Integer discarded;
    @SerializedName("Stale") private Integer stale;
    @SerializedName("Get Failures") private Integer getFailures;
    @SerializedName("Remote Failures") private Integer remoteFailures;
    @SerializedName("User") private String User;
    @SerializedName("Last Share Time") private Long lastShareTime;
    @SerializedName("Diff1 Shares") private Long diff1Shares;
    @SerializedName("Proxy Type") private String proxyType;
    @SerializedName("Proxy") private String proxy;
    @SerializedName("Difficulty Accepted") private BigDecimal difficultyAccepted;
    @SerializedName("Difficulty Rejected") private BigDecimal difficultyRejected;
    @SerializedName("Difficulty Stale") private BigDecimal difficultyStale;
    @SerializedName("Last Share Difficulty") private BigDecimal lastShareDifficulty;
    @SerializedName("Has Stratum") private Boolean hasStratum;
    @SerializedName("Stratum Active") private Boolean stratumActive;
    @SerializedName("Stratum URL") private String stratumURL;
    @SerializedName("Has GBT") private Boolean hasGBT;
    @SerializedName("Best Share") private Integer bestShare;
    @SerializedName("Pool Rejected%") private BigDecimal poolRejectedPercent;
    @SerializedName("Pool Stale%") private BigDecimal poolStalePercent;

    public Integer getPooL() {
        return pooL;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getQuota() {
        return quota;
    }

    public String getLongPoll() {
        return longPoll;
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

    public Integer getDiscarded() {
        return discarded;
    }

    public Integer getStale() {
        return stale;
    }

    public Integer getGetFailures() {
        return getFailures;
    }

    public Integer getRemoteFailures() {
        return remoteFailures;
    }

    public String getUser() {
        return User;
    }

    public Long getLastShareTime() {
        return lastShareTime;
    }

    public Long getDiff1Shares() {
        return diff1Shares;
    }

    public String getProxyType() {
        return proxyType;
    }

    public String getProxy() {
        return proxy;
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

    public BigDecimal getLastShareDifficulty() {
        return lastShareDifficulty;
    }

    public Boolean getHasStratum() {
        return hasStratum;
    }

    public Boolean getStratumActive() {
        return stratumActive;
    }

    public String getStratumURL() {
        return stratumURL;
    }

    public Boolean getHasGBT() {
        return hasGBT;
    }

    public Integer getBestShare() {
        return bestShare;
    }

    public BigDecimal getPoolRejectedPercent() {
        return poolRejectedPercent;
    }

    public BigDecimal getPoolStalePercent() {
        return poolStalePercent;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "pooL=" + pooL +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", quota=" + quota +
                ", longPoll='" + longPoll + '\'' +
                ", getworks=" + getworks +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", discarded=" + discarded +
                ", stale=" + stale +
                ", getFailures=" + getFailures +
                ", remoteFailures=" + remoteFailures +
                ", User='" + User + '\'' +
                ", lastShareTime=" + lastShareTime +
                ", diff1Shares=" + diff1Shares +
                ", proxyType='" + proxyType + '\'' +
                ", proxy='" + proxy + '\'' +
                ", difficultyAccepted=" + difficultyAccepted +
                ", difficultyRejected=" + difficultyRejected +
                ", difficultyStale=" + difficultyStale +
                ", lastShareDifficulty=" + lastShareDifficulty +
                ", hasStratum=" + hasStratum +
                ", stratumActive=" + stratumActive +
                ", stratumURL='" + stratumURL + '\'' +
                ", hasGBT=" + hasGBT +
                ", bestShare=" + bestShare +
                ", poolRejectedPercent=" + poolRejectedPercent +
                ", poolStalePercent=" + poolStalePercent +
                '}';
    }
}
