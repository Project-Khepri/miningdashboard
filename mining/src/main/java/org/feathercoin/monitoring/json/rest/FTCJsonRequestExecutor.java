package org.feathercoin.monitoring.json.rest;

import org.feathercoin.monitoring.CurrencyValueFormatter;
import org.feathercoin.monitoring.dto.FtcBalanceResponse;
import org.feathercoin.monitoring.dto.FtcDifficultyResponse;
import org.feathercoin.monitoring.dto.FtcStatsResponse;
import org.feathercoin.monitoring.dto.FtcValueResponse;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * JSON request executor for the <a href="https://api.feathercoin.com">Feathercoin JSON API</a>. API URLs have to be provided via
 * <ul>
 * <li>ftcapi.domain - Domain for the feathercoin api (is prefix for all provided URLs)</li>
 * <li>ftcapi.json.balance.url - JSON URL to fetch the balance for a given FTC address ({address}). E.g. ?output=balance&address={address}&json=1</li>
 * <li>ftcapi.json.difficulty.url - JSON URL to fetch the current difficulty</li>
 * <li>ftcapi.json.ftc_usd.url - JSON URL to fetch to USD value for a given FTC amount ({amount}). E.g ?output=usd&amount={amount}&json=1</li>
 * </ul>
 */
@Component
public class FTCJsonRequestExecutor extends AbstractJsonRequestExecutor implements Serializable{
    @Value("${ftcapi.json.balance.url}")
    private String JSON_BALANCE_URL;

    @Value("${ftcapi.json.difficulty.url}")
    private String JSON_DIFFICULTY_URL;

    @Value("${ftcapi.json.ftc_usd.url}")
    private String JSON_FTC_USD;

    @Value("${ftcapi.json.ftc_stats.url}")
    private String JSON_FTC_STATS;

    @Value("${ftcapi.domain}")
    private String DOMAIN;


    /**
     * Retrieve current amount of feathercoins for the given feathercoin address
     * @param ftcAddress FTC address to fetch data for
     * @return FtcBalanceResponse with the current balance for the address
     */
    public FtcBalanceResponse fetchCurrentBalance(String ftcAddress){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("address",ftcAddress);

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_BALANCE_URL, vars);

        JsonResponseTransformer<FtcBalanceResponse> ftcBalanceResponseJsonResponseTransformer =
                new JsonResponseTransformer<FtcBalanceResponse>(FtcBalanceResponse.class);

        return ftcBalanceResponseJsonResponseTransformer.transform(response);
    }

    /**
     * Fetches the current FTC network difficulty
     * @return FtcDifficultyResponse with the current network difficulty
     */
    public FtcDifficultyResponse fetchCurrentDifficulty(){

        HashMap<String,String> vars = new HashMap<String, String>();

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_DIFFICULTY_URL, vars);

        JsonResponseTransformer<FtcDifficultyResponse> ftcDifficultyResponseJsonResponseTransformer =
                new JsonResponseTransformer<FtcDifficultyResponse>(FtcDifficultyResponse.class);

        return ftcDifficultyResponseJsonResponseTransformer.transform(response);
    }

    /**
     * Fetches the current value in USD for the given amount of FTCs
     * @param amount Amount of FTCs to calculate the $value for
     * @return FtcValueResponse with the according value in $
     */
    public FtcValueResponse fetchCurrentUsdValue(BigDecimal amount){

        HashMap<String,String> vars = new HashMap<String, String>();
        vars.put("amount", CurrencyValueFormatter.formatToEnCurrency(amount));

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_FTC_USD, vars);

        JsonResponseTransformer<FtcValueResponse> ftcValueResponse =
                new JsonResponseTransformer<FtcValueResponse>(FtcValueResponse.class);

        return ftcValueResponse.transform(response);
    }

    /**
     * Fetches FTC statistics
     * @return FtcStatsResponse containing the FTC statistics
     */
    public FtcStatsResponse fetchFtcStatistics(){
        HashMap<String,String> vars = new HashMap<String, String>();

        String response = getJsonRequestExecutor().executeJsonRequest(DOMAIN,JSON_FTC_STATS, vars);

        JsonResponseTransformer<FtcStatsResponse> ftcDifficultyResponseJsonResponseTransformer =
                new JsonResponseTransformer<FtcStatsResponse>(FtcStatsResponse.class);

        return ftcDifficultyResponseJsonResponseTransformer.transform(response);
    }
}
