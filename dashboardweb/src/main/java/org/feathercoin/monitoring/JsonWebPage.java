package org.feathercoin.monitoring;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.feathercoin.monitoring.domain.MiningStats;
import org.feathercoin.monitoring.json.MiningStatsJsonConverter;

import java.util.List;

public class JsonWebPage extends WebPage {
    @SpringBean private FeathercoinProductionService feathercoinProductionService;
    @SpringBean private MiningStatsJsonConverter miningStatsJsonConverter;


    public JsonWebPage(PageParameters pageParameters) {
        List<MiningStats> currentMonthMiningStats = feathercoinProductionService.getLast30DaysMiningStats();

        //String json = "{\"data\":[{\"name\":\"13.12.2013\",\"value\":4},{\"name\":\"14.12.2013\",\"value\":20}]}";
        String json = miningStatsJsonConverter.transformToJson(currentMonthMiningStats);

        //allow requests from local files too to simplify testing of chart creation
        ((WebResponse)getResponse()).setHeader("Access-Control-Allow-Origin","*");
        getRequestCycle().scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", json));
    }
}
