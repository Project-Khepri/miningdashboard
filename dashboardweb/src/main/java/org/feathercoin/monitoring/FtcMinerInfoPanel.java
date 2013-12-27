package org.feathercoin.monitoring;


import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.feathercoin.monitoring.beans.SummaryBean;
import org.feathercoin.monitoring.dto.Dev;
import org.feathercoin.monitoring.dto.DevResponse;
import org.feathercoin.monitoring.dto.Summary;
import org.feathercoin.monitoring.dto.SummaryResponse;
import org.feathercoin.monitoring.json.JsonResponseTransformer;
import org.feathercoin.monitoring.json.MinerUdpCall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FtcMinerInfoPanel extends Panel implements Serializable{
    private MinerConnections minerConnections;

    public FtcMinerInfoPanel(String id, MinerConnections minerConnections){
        super(id);
        this.minerConnections = minerConnections;

        addMinerSummaries();

    }

    private void addMinerSummaries() {
        final List<IModel<MinerUdpCall>> summaries = new ArrayList<IModel<MinerUdpCall>>();
        List<MinerUdpCall> miners = minerConnections.getMiners();
        for (MinerUdpCall miner : miners) {
            summaries.add(Model.of(miner));
        }

        add(new RefreshingView<MinerUdpCall>("minerSummaries") {
            @Override
            protected void populateItem(final Item<MinerUdpCall> item) {
                item.add(new AjaxLazyLoadPanel("lazyLoadFtcSingleMinerInfo")
                {
                    @Override
                    public Component getLazyLoadComponent(String id)
                    {
                        SummaryBean summaryBean = createMinerSummary(item.getModelObject());
                        return new FtcSingleMinerInfoPanel(id,summaryBean);
                    }
                });
            }

            @Override
            protected Iterator<IModel<MinerUdpCall>> getItemModels() {
                return summaries.iterator();
            }
        });
    }

    private SummaryBean createMinerSummary(MinerUdpCall miner) {
        try{
            SummaryBean summaryBean = addMinerStats(miner, miner.getIp().getHostAddress());
            addGpuStats(miner,summaryBean);
            return summaryBean;
        }catch(Exception e){
            return createErrorMinerSummary(miner.getIp().getHostAddress());
        }
    }

    private SummaryBean addGpuStats(MinerUdpCall miner, SummaryBean summaryBean) {
        String response = miner.process("{\"command\":\"devs\"}");
        JsonResponseTransformer<DevResponse> transformer = new JsonResponseTransformer<DevResponse>(DevResponse.class);
        DevResponse devResponse = transformer.transform(response);
        List<Dev> devs = devResponse.getDevs();
        summaryBean.getGpus().addAll(devs);
        summaryBean.setGpuSummaryJson(response);


        return summaryBean;
    }


    private SummaryBean createErrorMinerSummary(String ipAddress){
        SummaryBean summaryBean = new SummaryBean();
        summaryBean.setIp(ipAddress);
        summaryBean.setHasErrors(true);
        return summaryBean;
    }

    private SummaryBean addMinerStats(MinerUdpCall miner, String ip) {
        String response = null;
        int repeatCounter = 0;
        while(true){
            if(repeatCounter>3)
                break;
            try{
                response = miner.process("{\"command\":\"summary\"}");
                break;
            }catch(Exception e){
                repeatCounter++;
                System.out.println("repeat call for ip "+ip);
            }
        }
        JsonResponseTransformer<SummaryResponse> transformer = new JsonResponseTransformer<SummaryResponse>(SummaryResponse.class);
        SummaryResponse summaryResponse = transformer.transform(response);
        Summary summary = summaryResponse.getFirstSummary();
        return new SummaryBean(summary.getMhs5s(),summary.getMhsAv(),response,ip);
    }
}
