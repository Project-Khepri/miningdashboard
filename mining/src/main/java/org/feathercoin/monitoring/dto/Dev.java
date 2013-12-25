package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;


public class Dev implements Serializable{
    @SerializedName("GPU") private Integer gpu;
    @SerializedName("Enabled") private String enabled;
    @SerializedName("Status") private String status;
    @SerializedName("Temperature") private BigDecimal temperature;
    @SerializedName("Fan Speed") private Integer fanSpeed;
    @SerializedName("Fan Percent") private Integer fanPercent;
    @SerializedName("GPU Clock") private Integer gpuClock;
    @SerializedName("Memory Clock") private Integer memoryClock;
    @SerializedName("GPU Voltage") private BigDecimal gpuVoltage;
    @SerializedName("GPU Activity") private Integer gpuActivity;
    @SerializedName("Powertune") private Integer powertune;
    @SerializedName("MHS av") private BigDecimal mhsAverage;
    @SerializedName("MHS 5s") private BigDecimal mhs5s;
    @SerializedName("Accepted") private Integer accepted;
    @SerializedName("Rejected") private Integer rejected;
    @SerializedName("Hardware Errors") private Integer hardwareErrors;
    @SerializedName("Utility") private BigDecimal utility;
    @SerializedName("Intensity") private Integer intensity;
    @SerializedName("Last Share Pool") private Integer lastSharePool;
    @SerializedName("Last Share Time") private Long lastShareTime;
    @SerializedName("Total MH") private BigDecimal totalMH;
    @SerializedName("Diff1 Work") private Integer diff1Work;
    @SerializedName("Difficulty Accepted") private BigDecimal difficultyAccepted;
    @SerializedName("Difficulty Rejected") private BigDecimal difficultyRejected;
    @SerializedName("Last Share Difficulty") private String lastShareDifficulty;
    @SerializedName("Last Valid Work") private Long lastValidWork;
    @SerializedName("Device Hardware%") private BigDecimal deviceHardwarePercent;
    @SerializedName("Device Rejected%") private BigDecimal deviceRejectedPercent;
    @SerializedName("Device Elapsed") private Integer elapsed;


    public Integer getGpu() {
        return gpu;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public Integer getFanSpeed() {
        return fanSpeed;
    }

    public Integer getFanPercent() {
        return fanPercent;
    }

    public Integer getGpuClock() {
        return gpuClock;
    }

    public Integer getMemoryClock() {
        return memoryClock;
    }

    public BigDecimal getGpuVoltage() {
        return gpuVoltage;
    }

    public Integer getGpuActivity() {
        return gpuActivity;
    }

    public Integer getPowertune() {
        return powertune;
    }

    public BigDecimal getMhsAverage() {
        return mhsAverage;
    }

    public BigDecimal getMhs5s() {
        return mhs5s;
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

    public Integer getIntensity() {
        return intensity;
    }

    public Integer getLastSharePool() {
        return lastSharePool;
    }

    public Long getLastShareTime() {
        return lastShareTime;
    }

    public BigDecimal getTotalMH() {
        return totalMH;
    }

    public Integer getDiff1Work() {
        return diff1Work;
    }

    public BigDecimal getDifficultyAccepted() {
        return difficultyAccepted;
    }

    public BigDecimal getDifficultyRejected() {
        return difficultyRejected;
    }

    public String getLastShareDifficulty() {
        return lastShareDifficulty;
    }

    public Long getLastValidWork() {
        return lastValidWork;
    }

    public BigDecimal getDeviceHardwarePercent() {
        return deviceHardwarePercent;
    }

    public BigDecimal getDeviceRejectedPercent() {
        return deviceRejectedPercent;
    }

    public Integer getElapsed() {
        return elapsed;
    }

    @Override
    public String toString() {
        return "Dev{" +
                "gpu=" + gpu +
                ", enabled='" + enabled + '\'' +
                ", status='" + status + '\'' +
                ", temperature=" + temperature +
                ", fanSpeed=" + fanSpeed +
                ", fanPercent=" + fanPercent +
                ", gpuClock=" + gpuClock +
                ", memoryClock=" + memoryClock +
                ", gpuVoltage=" + gpuVoltage +
                ", gpuActivity=" + gpuActivity +
                ", powertune=" + powertune +
                ", mhsAverage=" + mhsAverage +
                ", mhs5s=" + mhs5s +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", hardwareErrors=" + hardwareErrors +
                ", utility=" + utility +
                ", intensity=" + intensity +
                ", lastSharePool=" + lastSharePool +
                ", lastShareTime=" + lastShareTime +
                ", totalMH=" + totalMH +
                ", diff1Work=" + diff1Work +
                ", difficultyAccepted=" + difficultyAccepted +
                ", difficultyRejected=" + difficultyRejected +
                ", lastShareDifficulty='" + lastShareDifficulty + '\'' +
                ", lastValidWork=" + lastValidWork +
                ", deviceHardwarePercent=" + deviceHardwarePercent +
                ", deviceRejectedPercent=" + deviceRejectedPercent +
                ", elapsed=" + elapsed +
                '}';
    }
}
