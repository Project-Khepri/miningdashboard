package org.feathercoin.monitoring;


import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.feathercoin.monitoring.beans.SummaryBean;
import org.feathercoin.monitoring.dto.Dev;
import org.feathercoin.monitoring.util.AddLabelArrayUtil;
import org.feathercoin.monitoring.util.LastShareTimeConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FtcSingleMinerInfoPanel extends Panel implements Serializable{
    private SummaryBean summaryBean;

    public FtcSingleMinerInfoPanel(String id, SummaryBean summaryBean){
        super(id);
        this.summaryBean = summaryBean;

        addMinerSummaries();

    }

    private void addMinerSummaries() {

        String[] labels = {"mhs5s","mhsav","json"};

        String ip = summaryBean.getIp();
        String minerLink = "http://"+ ip +"/miner.php?ref=30&pg=Mobile";
        ExternalLink minerLink_ = new ExternalLink("minerLink", minerLink);
        minerLink_.add(new Label("ip", ip));
        add(minerLink_);

        if (!summaryBean.isHasErrors()){
            for (String label : labels) {
                add(new Label(label, new PropertyModel(Model.of(summaryBean), label)));
            }

            add(new WebMarkupContainer("informationBox").setVisible(false));
            final List<IModel<Dev>> gpuSummaries = new ArrayList<IModel<Dev>>();
            List<Dev> gpuBeans = summaryBean.getGpus();
            for (Dev gpuBean : gpuBeans) {
                gpuSummaries.add(Model.of(gpuBean));
            }

            add(new RefreshingView<Dev>("gpuSummaries") {
                @Override
                protected Iterator<IModel<Dev>> getItemModels() {
                    return gpuSummaries.iterator();
                }

                @Override
                protected void populateItem(Item<Dev> itemInner) {
                    AddLabelArrayUtil.addLabels(itemInner,"gpu","temperature","hardwareErrors","mhs5s","enabled","status");
                    addLastShareTime(itemInner);
                    addStatusInfo(itemInner);
                }

                private void addStatusInfo(Item<Dev> itemInner) {
                    String status = itemInner.getModel().getObject().getStatus();
                    String picture = "ftc-small_green.png";
                    if ("Alive".equalsIgnoreCase(status) || "Sick".equalsIgnoreCase(status)){
                        picture = "ftc-small_green.png";
                    }else if ("Dead".equalsIgnoreCase(status) || "Sick".equalsIgnoreCase(status)){
                        picture = "ftc-small_red.png";
                    }else{
                        picture = "ftc-small_orange.png";
                    }
                    itemInner.add(new Image("gpuStatusSign",new PackageResourceReference(HomePage.class, picture)));
                }

                private void addLastShareTime(Item<Dev> itemInner) {
                    itemInner.add(new Label("lastShareTime",
                            LastShareTimeConverter.convertShareTime(itemInner.getModel().getObject().getLastShareTime())));
                }
            } );

            add(new Label("gpuJson", summaryBean.getGpuSummaryJson()));

            BigDecimal totalGpuHashrate = BigDecimal.ZERO;
            for (Dev gpuBean : gpuBeans) {
                totalGpuHashrate = totalGpuHashrate.add(gpuBean.getMhs5s());
            }

            add(new Label("totalGpuHashrate",totalGpuHashrate));

        }else{
            for (String label : labels) {
                add(new Label(label).setVisible(false));
            }
            add(new WebMarkupContainer("gpuSummaries").setVisible(false));
            add(new WebMarkupContainer("informationBox").setVisible(true));
            add(new Label("totalGpuHashrate").setVisible(false));
            add(new Label("gpuJson").setVisible(false));
        }

    }
}
