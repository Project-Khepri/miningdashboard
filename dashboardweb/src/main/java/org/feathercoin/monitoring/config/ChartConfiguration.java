package org.feathercoin.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ChartConfiguration implements Serializable{
    @Value("${chart.enabled}")
    private boolean chartEnabled=false;

    public boolean isChartEnabled() {
        return chartEnabled;
    }
}
