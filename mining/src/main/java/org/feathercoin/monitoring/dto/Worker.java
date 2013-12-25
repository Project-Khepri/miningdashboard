package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

public class Worker {
    @SerializedName("alive") private String alive;
    @SerializedName("hashrate") private String hashrate;
    @SerializedName("last_share_timestamp") private Long lastShareTimestamp;
    @SerializedName("username") private String username;

    public String getAlive() {
        return alive;
    }

    public String getHashrate() {
        return hashrate;
    }

    public Long getLastShareTimestamp() {
        return lastShareTimestamp;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "alive='" + alive + '\'' +
                ", hashrate='" + hashrate + '\'' +
                ", lastShareTimestamp=" + lastShareTimestamp +
                ", username='" + username + '\'' +
                '}';
    }
}
