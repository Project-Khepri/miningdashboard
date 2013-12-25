package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;

@Component
public class D2JsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${pool.d2.jsonUrl}")
    public String JSON_URL;

    @Value("${pool.d2.domain}")
    private String DOMAIN;


    public D2Response fetchD2Data(String apiKey){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("api_key",apiKey);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_URL, vars);
        System.out.println(response);

        JsonResponseTransformer<D2Response> giveMeCoinsResponseJsonResponseTransformer =
                new JsonResponseTransformer<D2Response>(D2Response.class);

        return giveMeCoinsResponseJsonResponseTransformer.transform(response);
    }
}
