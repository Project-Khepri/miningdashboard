package org.feathercoin.monitoring.json;

import com.google.gson.GsonBuilder;

public class JsonResponseTransformer<C> {
    private Class<C> type;

    public JsonResponseTransformer(Class<C> type) {
        this.type = type;
    }

    public C transform(String response){
        C jsonObject = null;
        try {
            jsonObject = (C)new GsonBuilder().setPrettyPrinting().create().fromJson(response, type.newInstance().getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

}
