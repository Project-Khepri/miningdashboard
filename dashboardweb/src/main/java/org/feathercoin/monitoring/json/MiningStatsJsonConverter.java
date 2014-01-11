package org.feathercoin.monitoring.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.feathercoin.monitoring.domain.MiningStats;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
public class MiningStatsJsonConverter implements Serializable{
    public String transformToJson(List<MiningStats> stats){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class,new DateTimeSerializer());
        Gson gson = gsonBuilder.create();


        String result = gson.toJson(stats);
        return result;
    }
}
