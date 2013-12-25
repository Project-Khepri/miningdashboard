package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

public class Config {
    @SerializedName("GPU Count") private Integer gpuCount;
    @SerializedName("ASC Count") private Integer ascCount;
    @SerializedName("PGA Count") private Integer pgaCount;
    @SerializedName("Pool Count") private Integer poolCount;
    @SerializedName("ADL") private String adl;
    @SerializedName("ADL in use") private String adlInUse;
    @SerializedName("Strategy") private String Strategy;
    @SerializedName("Log Interval") private Integer logInterval;
    @SerializedName("Device Code") private String deviceCode;
    @SerializedName("OS") private String os;
    @SerializedName("Failover-Only") private Boolean failoverOnly;
    @SerializedName("ScanTime") private Integer scanTime;
    @SerializedName("Queue") private Integer queue;
    @SerializedName("Expiry") private Integer expiry;
    @SerializedName("Hotplug") private Integer hotplug;

    public Integer getGpuCount() {
        return gpuCount;
    }

    public Integer getAscCount() {
        return ascCount;
    }

    public Integer getPgaCount() {
        return pgaCount;
    }

    public Integer getPoolCount() {
        return poolCount;
    }

    public String getAdl() {
        return adl;
    }

    public String getAdlInUse() {
        return adlInUse;
    }

    public String getStrategy() {
        return Strategy;
    }

    public Integer getLogInterval() {
        return logInterval;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getOs() {
        return os;
    }

    public Boolean getFailoverOnly() {
        return failoverOnly;
    }

    public Integer getScanTime() {
        return scanTime;
    }

    public Integer getQueue() {
        return queue;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public Integer getHotplug() {
        return hotplug;
    }

    @Override
    public String toString() {
        return "Config{" +
                "gpuCount=" + gpuCount +
                ", ascCount=" + ascCount +
                ", pgaCount=" + pgaCount +
                ", poolCount=" + poolCount +
                ", adl='" + adl + '\'' +
                ", adlInUse='" + adlInUse + '\'' +
                ", Strategy='" + Strategy + '\'' +
                ", logInterval=" + logInterval +
                ", deviceCode='" + deviceCode + '\'' +
                ", os='" + os + '\'' +
                ", failoverOnly=" + failoverOnly +
                ", scanTime=" + scanTime +
                ", queue=" + queue +
                ", expiry=" + expiry +
                ", hotplug=" + hotplug +
                '}';
    }
}
