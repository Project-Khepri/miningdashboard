package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FtcDifficultyResponse {
    @SerializedName("nowdiff") private BigDecimal difficulty;

    public BigDecimal getDifficulty() {
        return difficulty;
    }
}
