package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class FtcBalanceResponse {
    @SerializedName("balance") private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }
}
