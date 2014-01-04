package org.feathercoin.monitoring;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.feathercoin.monitoring.config.ChartConfiguration;
import org.feathercoin.monitoring.config.FtcConfiguration;
import org.feathercoin.monitoring.json.rest.FTCJsonRequestExecutor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @SpringBean private PoolInformationProvider poolInformationProvider;
    @SpringBean private FTCJsonRequestExecutor ftcJsonRequestExecutor;
    @SpringBean private MinerConnections miners;
    @SpringBean private FtcConfiguration ftcConfiguration;
    @SpringBean private ChartConfiguration chartConfiguration;


	public HomePage(final PageParameters parameters) {
		super(parameters);

        Date refreshStart = new Date();
        Date lastRefresh = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        add(new Label("lastRefresh",sdf.format(lastRefresh)));
        add(new Label("refreshTime",calculateRefreshTimeInMillisecondsAndSeconds(refreshStart,lastRefresh)));



        add(new AjaxLazyLoadPanel("lazyLoadFtcMarketValues")
        {
            @Override
            public Component getLazyLoadComponent(String id)
            {
                return new FtcValuePanel(id,ftcJsonRequestExecutor,ftcConfiguration.getFtcAddress());
            }

        });

        add(new AjaxLazyLoadPanel("lazyLoadFtcPoolInfos")
        {
            @Override
            public Component getLazyLoadComponent(String id)
            {
                return new FtcPoolsPanel(id,poolInformationProvider);
            }

        });

        add(new AjaxLazyLoadPanel("lazyLoadFtcMinerInfos")
        {
            @Override
            public Component getLazyLoadComponent(String id)
            {
                return new FtcMinerInfoPanel(id,miners);
            }

        });

    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);
        if (chartConfiguration.isChartEnabled()){
            response.render(OnDomReadyHeaderItem.forScript("$(document).ready(function() {\n" +
                    "            console.log('ready!');\n" +
                    "            renderChart();\n" +
                    "        });"));
        }
    }

    private String calculateRefreshTimeInMillisecondsAndSeconds(Date refreshStart, Date lastRefresh) {
        long diff = lastRefresh.getTime()-refreshStart.getTime();
        BigDecimal diffInSeconds = new BigDecimal(diff);
        diffInSeconds = diffInSeconds.divide(BigDecimal.valueOf(1000),2, RoundingMode.HALF_UP);

        return ""+diff+"ms ... "+diffInSeconds.toString()+"s";
    }
}
