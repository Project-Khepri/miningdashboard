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
import org.feathercoin.monitoring.util.AddLabelArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PoolInformationProvider implements Serializable{
    private String POOL_D2_LOGIN = "https://ftc.d2.cc/login.php";
    private String POOL_GIVE_ME_COINS_LOGIN = "https://give-me-coins.com/pool/auth";

    @Autowired PoolDataFetcher poolDataFetcher;
    @Autowired PoolTotalSummaryCalculator poolTotalSummaryCalculator;

    public void addPoolInformation(MarkupContainer parent) {
        List<PoolInfoBean> poolInfoBeans = poolDataFetcher.fetchPoolInformation();
        final List<IModel<PoolInfoBean>> poolInfos = new ArrayList<IModel<PoolInfoBean>>();
        for (PoolInfoBean poolInfoBean : poolInfoBeans) {
            poolInfos.add(Model.of(poolInfoBean));
        }


        poolInfos.add(Model.of(poolTotalSummaryCalculator.calculateTotal(poolInfoBeans)));

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
        if (PoolDataFetcher.POOL_D_2.equals(item.getModel().getObject().getPoolName()))
            poolLink = new ExternalLink("poolLogin", POOL_D2_LOGIN);
        else if (PoolDataFetcher.POOL_GIVE_ME_COINS.equals(item.getModel().getObject().getPoolName()))
            poolLink = new ExternalLink("poolLogin", POOL_GIVE_ME_COINS_LOGIN);
        else {
            poolLink = new ExternalLink("poolLogin", "javascript:void(0)");
            poolLink.setVisible(false);
        }
        item.add(poolLink);
    }
}
