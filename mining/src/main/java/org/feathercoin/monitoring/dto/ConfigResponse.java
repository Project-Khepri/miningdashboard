package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConfigResponse extends CGMinerReponse {
    @SerializedName("CONFIG") private ArrayList<Config> config = new ArrayList<Config>();
    public Config getFirstConfig(){
        if (!config.isEmpty())
            return config.get(0);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " ConfigResponse{" +
                "config=" + config +
                '}';
    }
}
