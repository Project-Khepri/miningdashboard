package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SummaryResponse extends CGMinerReponse {
    @SerializedName("SUMMARY") private ArrayList<Summary> summary = new ArrayList<Summary>();
    public Summary getFirstSummary(){
        if (!summary.isEmpty())
            return summary.get(0);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " SummaryResponse{" +
                "summary=" + summary +
                '}';
    }
}
