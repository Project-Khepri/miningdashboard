package org.feathercoin.monitoring.config;

import java.util.ArrayList;
import java.util.List;

public class MinerJsonConfig {
    private ArrayList<MinerConnectionConfig> miners = new ArrayList<MinerConnectionConfig>();

    public List<MinerConnectionConfig> getMiners() {
        return miners;
    }
}
