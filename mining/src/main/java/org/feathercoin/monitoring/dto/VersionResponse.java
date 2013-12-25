package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VersionResponse extends CGMinerReponse {
    @SerializedName("VERSION") private ArrayList<Version> version = new ArrayList<Version>();
    public Version getFirstVersion(){
        if (!version.isEmpty())
            return version.get(0);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " VersionResponse{" +
                "version=" + version +
                '}';
    }
}
