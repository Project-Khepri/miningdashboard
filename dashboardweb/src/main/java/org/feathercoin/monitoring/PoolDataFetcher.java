package org.feathercoin.monitoring;

import org.feathercoin.monitoring.beans.PoolInfoBean;
import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.feathercoin.monitoring.json.rest.D2JsonRequestExecutor;
import org.feathercoin.monitoring.json.rest.GiveMeCoinsJsonRequestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class PoolDataFetcher implements Serializable{
    public static String POOL_GIVE_ME_COINS = "Give-Me-Coins";
    public static String POOL_D_2 = "D2";


    @Value("${pool.giveMeCoins.apiKey:@null}")
    private String GIVE_ME_COINS_API_KEY;

    @Value("${pool.d2.apiKey:@null}")
    private String POOL_D2_API_KEY;

    @Autowired
    private GiveMeCoinsJsonRequestExecutor giveMeCoinsJsonRequestExecutor;
    @Autowired private D2JsonRequestExecutor requestExecutorD2;

    private GiveMeCoinsResponse fetchGiveMeCoinsPoolInfo() {
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

    private D2Response fetchD2PoolInfo() {
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

    public List<PoolInfoBean> fetchPoolInformation(){
        ArrayList<PoolInfoBean> poolInfoBeans = new ArrayList<PoolInfoBean>();
        poolInfoBeans.add(addD2PoolSummary(fetchD2PoolInfo()));
        poolInfoBeans.add(addGmcPoolSummary(fetchGiveMeCoinsPoolInfo()));
        return poolInfoBeans;
    }

    private PoolInfoBean addD2PoolSummary(D2Response d2response) {
        if (d2response==D2Response.EMPTY_RESPONSE)
            return new PoolInfoBean(true, POOL_D_2);
        PoolInfoBean poolInfoBean = new PoolInfoBean(
                d2response.getPayoutHistory(),
                d2response.getTotalHashrate(),
                d2response.getRoundEstimate(),
                d2response.getConfirmedRewards(),
                POOL_D_2
        );
        return poolInfoBean;
    }

    private PoolInfoBean addGmcPoolSummary(GiveMeCoinsResponse gmcResponse) {
        if (gmcResponse==GiveMeCoinsResponse.EMPTY_RESPONSE)
            return new PoolInfoBean(true, POOL_GIVE_ME_COINS);
        PoolInfoBean poolInfoBean = new PoolInfoBean(
                gmcResponse.getPayoutHistory(),
                gmcResponse.getTotalHashrate(),
                gmcResponse.getRoundEstimate(),
                gmcResponse.getConfirmedRewards(),
                POOL_GIVE_ME_COINS
        );
        return poolInfoBean;
    }
}
