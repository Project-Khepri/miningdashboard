package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FtcStatsResponse {
    @SerializedName("currblk") private Integer currentBlock;
    @SerializedName("nowdiff") private BigDecimal currentDiff;
    @SerializedName("nextdiff") private BigDecimal nextDiff;

    public Integer getCurrentBlock() {
        return currentBlock;
    }

    public BigDecimal getCurrentDiff() {
        return currentDiff;
    }

    public BigDecimal getNextDiff() {
        return nextDiff;
    }
}
