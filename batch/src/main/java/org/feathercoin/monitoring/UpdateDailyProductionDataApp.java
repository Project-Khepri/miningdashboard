package org.feathercoin.monitoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class UpdateDailyProductionDataApp {

    public static final int MAX_RETRIES = 5;
    private static final Log log = LogFactory.getLog(UpdateDailyProductionDataApp.class);

    public static void main(String... args){
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/org/feathercoin/monitoring/dataApplicationContext.xml");
        FeathercoinStatsService service = applicationContext.getBean(FeathercoinStatsService.class);
        PoolInformationProvider poolInformationProvider = applicationContext.getBean(PoolInformationProvider.class);

        D2Response d2Response = fetchD2PoolInfo(poolInformationProvider);
        GiveMeCoinsResponse giveMeCoinsResponse = fetchGiveMeCoinsPoolInfo(poolInformationProvider);

        BigDecimal totalPayout = d2Response.getPayoutHistory().add(giveMeCoinsResponse.getPayoutHistory());
        log.debug("update dailyProduction with totalPayout "+totalPayout);
        service.updateDailyProduction(totalPayout);

    }

    private static GiveMeCoinsResponse fetchGiveMeCoinsPoolInfo(PoolInformationProvider poolInformationProvider) {
        int retryCounter = 0;
        GiveMeCoinsResponse giveMeCoinsResponse = null;
        while(isEmptyGiveMeCoinsResponse(giveMeCoinsResponse) && retryCounter<MAX_RETRIES){
            log.debug("try to fetch gmc pool information - try it the "+retryCounter+" time");
            try{
                giveMeCoinsResponse=poolInformationProvider.fetchGiveMeCoinsPoolInfo();
            }catch(Throwable e){
                log.error(e);
            }
            retryCounter++;
        }

        if (isEmptyGiveMeCoinsResponse(giveMeCoinsResponse)){
            log.error("Got no response from GiveMeCoins-Pool after "+retryCounter+" tries. Give up now");
            System.exit(1);
        }

        log.debug("got valid gmc response:<"+giveMeCoinsResponse.toString()+">");
        return giveMeCoinsResponse;
    }

    private static boolean isEmptyGiveMeCoinsResponse(GiveMeCoinsResponse giveMeCoinsResponse) {
        return (giveMeCoinsResponse==null || GiveMeCoinsResponse.EMPTY_RESPONSE.equals(giveMeCoinsResponse));
    }

    private static D2Response fetchD2PoolInfo(PoolInformationProvider poolInformationProvider) {
        int retryCounter = 0;
        D2Response d2Response = null;
        while(isEmptyD2Response(d2Response) && retryCounter<MAX_RETRIES){
            log.debug("try to fetch d2 pool information - try it the "+retryCounter+" time");
            try{
                d2Response=poolInformationProvider.fetchD2PoolInfo();
            }catch(Throwable e){
                log.error(e);
            }
            retryCounter++;
        }

        if (isEmptyD2Response(d2Response) ){
            log.error("Got no response from D2-Pool after "+retryCounter+" tries. Give up now");
            System.exit(1);
        }

        log.debug("got valid d2 response:<"+d2Response.toString()+">");
        return d2Response;
    }

    private static boolean isEmptyD2Response(D2Response d2Response) {
        return (d2Response==null || D2Response.EMPTY_RESPONSE.equals(d2Response));
    }
}
