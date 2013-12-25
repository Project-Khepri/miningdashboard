package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CGMinerReponse {

    @SerializedName("STATUS") private ArrayList<Status> status = new ArrayList<Status>();

    public Status getFirstStatus(){
        if (!status.isEmpty())
            return status.get(0);
        return null;
    }


    @Override
    public String toString() {
        return "CGMinerReponse{" +
                "status=" + status +
                '}';
    }
}
