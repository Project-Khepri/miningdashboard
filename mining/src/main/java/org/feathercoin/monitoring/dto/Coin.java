package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Coin {
    @SerializedName("Hash Method") private String hashMethod;
    @SerializedName("Current Block Time") private BigDecimal currentBlockTime;
    @SerializedName("Current Block Hash") private  String currentBlockHash;
    @SerializedName("LP") private Boolean lp;
    @SerializedName("Network Difficulty") private  BigDecimal networkDifficulty;

    public String getHashMethod() {
        return hashMethod;
    }

    public BigDecimal getCurrentBlockTime() {
        return currentBlockTime;
    }

    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    public Boolean getLp() {
        return lp;
    }

    public BigDecimal getNetworkDifficulty() {
        return networkDifficulty;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "hashMethod='" + hashMethod + '\'' +
                ", currentBlockTime=" + currentBlockTime +
                ", currentBlockHash='" + currentBlockHash + '\'' +
                ", lp=" + lp +
                ", networkDifficulty=" + networkDifficulty +
                '}';
    }
}
