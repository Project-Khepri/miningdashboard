package org.feathercoin.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChartConfiguration {
    @Value("${chart.enabled}")
    private boolean chartEnabled=false;

    public boolean isChartEnabled() {
        return chartEnabled;
    }
}
