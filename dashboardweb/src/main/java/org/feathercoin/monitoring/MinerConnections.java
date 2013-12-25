package org.feathercoin.monitoring;


import org.feathercoin.monitoring.json.MinerUdpCall;

import java.util.ArrayList;
import java.util.List;

public class MinerConnections {
    private ArrayList<MinerUdpCall> miners = new ArrayList<MinerUdpCall>();

    public void addMiner(MinerUdpCall minerUdpCall){
        miners.add(minerUdpCall);
    }

    public List<MinerUdpCall> getMiners() {
        return miners;
    }
}
