package org.feathercoin.monitoring;

import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.feathercoin.monitoring.json.rest.D2JsonRequestExecutor;
import org.feathercoin.monitoring.json.rest.GiveMeCoinsJsonRequestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PoolInformationProvider implements Serializable{
    private String POOL_GIVE_ME_COINS = "Give-Me-Coins";
    private String POOL_D_2 = "D2";
    private String POOL_D2_LOGIN = "https://ftc.d2.cc/login.php";
    private String POOL_GIVE_ME_COINS_LOGIN = "https://give-me-coins.com/pool/auth";

    @Value("${pool.giveMeCoins.apiKey:@null}")
    private String GIVE_ME_COINS_API_KEY;

    @Value("${pool.d2.apiKey:@null}")
    private String POOL_D2_API_KEY;


    @Autowired private GiveMeCoinsJsonRequestExecutor giveMeCoinsJsonRequestExecutor;
    @Autowired private D2JsonRequestExecutor requestExecutorD2;

    public GiveMeCoinsResponse fetchGiveMeCoinsPoolInfo() {
        GiveMeCoinsResponse giveMeCoinsResponse = null;
        if (GIVE_ME_COINS_API_KEY==null ||GIVE_ME_COINS_API_KEY.isEmpty())
            return GiveMeCoinsResponse.EMPTY_RESPONSE;

        try{
            giveMeCoinsResponse = giveMeCoinsJsonRequestExecutor.fetchGiveMeCoinsData(GIVE_ME_COINS_API_KEY);
        }catch(Throwable e){
            giveMeCoinsResponse = GiveMeCoinsResponse.EMPTY_RESPONSE;
        }
        return giveMeCoinsResponse;
    }

    public D2Response fetchD2PoolInfo() {
        D2Response d2response = null;
        if (POOL_D2_API_KEY==null ||POOL_D2_API_KEY.isEmpty())
            return D2Response.EMPTY_RESPONSE;
        try{
            d2response = requestExecutorD2.fetchD2Data(POOL_D2_API_KEY);
        }catch(Throwable e){
            d2response = D2Response.EMPTY_RESPONSE;
        }
        return d2response;
    }

}
