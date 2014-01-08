package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;

/**
 * JSON request executor for the  <a href="http://give-me-coins.com">GiveMeCoins Feathercoin mining pool</a>
 * Connection parameters have to be configured via the properties pool.giveMeCoins.jsonUrl and pool.giveMeCoins.domain.
 *
 * For fetching data via JSON from the GiveMeCoins pool you need to have an API-Key. You can get it from the settings
 * of your pool. The API key is an authentication token unique for your pool user.
 */

@Component
public class GiveMeCoinsJsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${pool.giveMeCoins.jsonUrl}")
    private String JSON_URL;

    @Value("${pool.giveMeCoins.domain}")
    private String DOMAIN;

    /**
     * Fetches pool information via a JSON call using the provided apiKey
     * @param apiKey For fetching data via JSON from the GiveMeCoins pool you need to have an API-Key. You can get it from the settings
     * of your pool. The API key is an authentication token unique for your pool user.
     * @return GiveMeCoinsResponse with values from GiveMeCoins-pool or GiveMeCoinsResponse.EMPTY_RESPONSE
     */
    public GiveMeCoinsResponse fetchGiveMeCoinsData(String apiKey){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("api_key",apiKey);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_URL, vars);

        JsonResponseTransformer<GiveMeCoinsResponse> giveMeCoinsResponseJsonResponseTransformer =
                new JsonResponseTransformer<GiveMeCoinsResponse>(GiveMeCoinsResponse.class);

        return giveMeCoinsResponseJsonResponseTransformer.transform(response);
    }
}
