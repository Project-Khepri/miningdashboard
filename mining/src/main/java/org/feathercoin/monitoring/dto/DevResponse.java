package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DevResponse extends CGMinerReponse {
    @SerializedName("DEVS") private ArrayList<Dev> dev = new ArrayList<Dev>();

    public List<Dev> getDevs(){
        return dev;
    }

    @Override
    public String toString() {
        return super.toString() + " DevResponse{" +
                "devs=" + dev +
                '}';
    }
}
