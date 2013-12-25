package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;

@Component
public class GiveMeCoinsJsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${pool.giveMeCoins.jsonUrl}")
    private String JSON_URL;

    @Value("${pool.giveMeCoins.domain}")
    private String DOMAIN;


    public GiveMeCoinsResponse fetchGiveMeCoinsData(String apiKey){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("api_key",apiKey);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_URL, vars);

        JsonResponseTransformer<GiveMeCoinsResponse> giveMeCoinsResponseJsonResponseTransformer =
                new JsonResponseTransformer<GiveMeCoinsResponse>(GiveMeCoinsResponse.class);

        return giveMeCoinsResponseJsonResponseTransformer.transform(response);
    }
}
