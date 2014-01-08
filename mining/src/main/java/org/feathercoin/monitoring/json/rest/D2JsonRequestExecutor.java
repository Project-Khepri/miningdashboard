package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;

/**
 * JSON request executor for the <a href="https://ftc.d2.cc/login.php">D2 Feathercoin mining pool</a>
 * Connection parameters have to be configured via the properties pool.d2.jsonUrl and pool.d2.domain.
 *
 * For fetching data via JSON from the D2 pool you need to have an API-Key. You can get it from the settings
 * of your pool. The API key is an authentication token unique for your pool user.
 */

@Component
public class D2JsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${pool.d2.jsonUrl}")
    public String JSON_URL;

    @Value("${pool.d2.domain}")
    private String DOMAIN;

    /**
     * Fetches pool information via a JSON call using the provided apiKey
     * @param apiKey For fetching data via JSON from the D2 pool you need to have an API-Key. You can get it from the settings
     * of your pool. The API key is an authentication token unique for your pool user.
     * @return D2Response with values from D2-pool or D2Response.EMPTY_RESPONSE
     */
    public D2Response fetchD2Data(String apiKey){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("api_key",apiKey);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_URL, vars);

        JsonResponseTransformer<D2Response> giveMeCoinsResponseJsonResponseTransformer =
                new JsonResponseTransformer<D2Response>(D2Response.class);

        return giveMeCoinsResponseJsonResponseTransformer.transform(response);
    }
}
