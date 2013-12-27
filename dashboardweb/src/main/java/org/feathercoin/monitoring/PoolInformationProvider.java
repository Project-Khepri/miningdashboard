package org.feathercoin.monitoring;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.feathercoin.monitoring.beans.PoolInfoBean;
import org.feathercoin.monitoring.dto.D2Response;
import org.feathercoin.monitoring.dto.GiveMeCoinsResponse;
import org.feathercoin.monitoring.json.rest.D2JsonRequestExecutor;
import org.feathercoin.monitoring.json.rest.GiveMeCoinsJsonRequestExecutor;
import org.feathercoin.monitoring.util.AddLabelArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void addPoolInformation(MarkupContainer parent) {
        GiveMeCoinsResponse giveMeCoinsResponse = fetchGiveMeCoinsPoolInfo();
        D2Response d2response = fetchD2PoolInfo();


        final List<IModel<PoolInfoBean>> poolInfos = new ArrayList<IModel<PoolInfoBean>>();

        poolInfos.add(Model.of(addD2PoolSummary(d2response)));
        poolInfos.add(Model.of(addGmcPoolSummary(giveMeCoinsResponse)));
        poolInfos.add(Model.of(addTotalPoolSummary(giveMeCoinsResponse, d2response)));

        parent.add(new RefreshingView<PoolInfoBean>("poolInfos") {
            @Override
            protected void populateItem(Item<PoolInfoBean> item) {
                item.add(new Label("poolName", new PropertyModel(item.getModel(), "poolName")));

                addPoolLink(item);

                if (item.getModel().getObject().isHasErrors()){
                    item.add(new WebMarkupContainer("poolInformationBox").setVisible(true));
                    item.add(new WebMarkupContainer("singlePoolInfo").setVisible(false));
                    return;
                }

                item.add(new WebMarkupContainer("poolInformationBox").setVisible(false));
                WebMarkupContainer singlePoolInfo = new WebMarkupContainer("singlePoolInfo");
                singlePoolInfo.setVisible(true);
                item.add(singlePoolInfo);

                AddLabelArrayUtil.addLabels(item,singlePoolInfo,"payoutHistory","totalHashrate","roundEstimate","confirmedRewards");
            }

            @Override
            protected Iterator<IModel<PoolInfoBean>> getItemModels() {
                return poolInfos.iterator();
            }
        });
    }

    private void addPoolLink(Item<PoolInfoBean> item) {
        ExternalLink poolLink = null;
        if (POOL_D_2.equals(item.getModel().getObject().getPoolName()))
            poolLink = new ExternalLink("poolLogin", POOL_D2_LOGIN);
        else if (POOL_GIVE_ME_COINS.equals(item.getModel().getObject().getPoolName()))
            poolLink = new ExternalLink("poolLogin", POOL_GIVE_ME_COINS_LOGIN);
        else {
            poolLink = new ExternalLink("poolLogin", "javascript:void(0)");
            poolLink.setVisible(false);
        }
        item.add(poolLink);
    }


    private PoolInfoBean addTotalPoolSummary(GiveMeCoinsResponse giveMeCoinsResponse, D2Response d2response) {
        PoolInfoBean poolInfoBean = new PoolInfoBean(
                d2response.getPayoutHistory().add(giveMeCoinsResponse.getPayoutHistory()),
                d2response.getTotalHashrate()+giveMeCoinsResponse.getTotalHashrate(),
                d2response.getRoundEstimate().add(giveMeCoinsResponse.getRoundEstimate()),
                d2response.getConfirmedRewards().add(giveMeCoinsResponse.getConfirmedRewards()),
                "Total"
        );
        return poolInfoBean;
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
