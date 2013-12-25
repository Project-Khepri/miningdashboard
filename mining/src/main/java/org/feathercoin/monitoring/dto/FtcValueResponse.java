package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FtcValueResponse {
    @SerializedName("usd") private BigDecimal usdValue;

    public BigDecimal getUsdValue() {
        return usdValue;
    }
}
