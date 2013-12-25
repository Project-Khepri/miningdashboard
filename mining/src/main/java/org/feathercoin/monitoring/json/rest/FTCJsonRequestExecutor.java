package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.json.rest.AbstractJsonRequestExecutor;
import org.feathercoin.monitoring.CurrencyValueFormatter;
import org.feathercoin.monitoring.dto.FtcBalanceResponse;
import org.feathercoin.monitoring.dto.FtcDifficultyResponse;
import org.feathercoin.monitoring.dto.FtcValueResponse;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class FTCJsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${ftcapi.json.balance.url}")
    private String JSON_BALANCE_URL;

    @Value("${ftcapi.json.difficulty.url}")
    private String JSON_DIFFICULTY_URL;

    @Value("${ftcapi.json.ftc_usd.url}")
    private String JSON_FTC_USD;

    @Value("${ftcapi.domain}")
    private String DOMAIN;


    public FtcBalanceResponse fetchCurrentBalance(String ftcAddress){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("address",ftcAddress);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_BALANCE_URL, vars);

        JsonResponseTransformer<FtcBalanceResponse> ftcBalanceResponseJsonResponseTransformer =
                new JsonResponseTransformer<FtcBalanceResponse>(FtcBalanceResponse.class);

        return ftcBalanceResponseJsonResponseTransformer.transform(response);
    }

    public FtcDifficultyResponse fetchCurrentDifficulty(){

        HashMap<String,String> vars = new HashMap<String, String>();

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_DIFFICULTY_URL, vars);

        JsonResponseTransformer<FtcDifficultyResponse> ftcDifficultyResponseJsonResponseTransformer =
                new JsonResponseTransformer<FtcDifficultyResponse>(FtcDifficultyResponse.class);

        return ftcDifficultyResponseJsonResponseTransformer.transform(response);
    }

    public FtcValueResponse fetchCurrentUsdValue(BigDecimal amount){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("amount", CurrencyValueFormatter.formatToEnCurrency(amount));

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_FTC_USD, vars);

        JsonResponseTransformer<FtcValueResponse> ftcValueResponse =
                new JsonResponseTransformer<FtcValueResponse>(FtcValueResponse.class);

        return ftcValueResponse.transform(response);
    }
}
