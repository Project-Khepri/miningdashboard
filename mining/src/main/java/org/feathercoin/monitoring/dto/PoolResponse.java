package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PoolResponse extends CGMinerReponse {
    @SerializedName("POOLS") private ArrayList<Pool> pool = new ArrayList<Pool>();
    public Pool getFirstPool(){
        if (!pool.isEmpty())
            return pool.get(0);
        return null;
    }

    public List<Pool> getPools(){
        return pool;
    }


    @Override
    public String toString() {
        return super.toString() + " PoolResponse{" +
                "pools=" + pool +
                '}';
    }
}
