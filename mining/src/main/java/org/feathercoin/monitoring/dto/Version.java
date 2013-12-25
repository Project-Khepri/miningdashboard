package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

public class Version {
    @SerializedName("CGMiner") private String cgminer;
    @SerializedName("API") private String api;

    public String getCgminer() {
        return cgminer;
    }

    public String getApi() {
        return api;
    }

    @Override
    public String toString() {
        return "Version{" +
                "cgminer='" + cgminer + '\'' +
                ", api='" + api + '\'' +
                '}';
    }
}
