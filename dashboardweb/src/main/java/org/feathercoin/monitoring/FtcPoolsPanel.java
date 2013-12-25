package org.feathercoin.monitoring;


import org.apache.wicket.markup.html.panel.Panel;

import java.io.Serializable;

public class FtcPoolsPanel extends Panel implements Serializable{
    private PoolInformationProvider poolInformationProvider;

    public FtcPoolsPanel(String id, PoolInformationProvider poolInformationProvider){
        super(id);
        this.poolInformationProvider = poolInformationProvider;
        poolInformationProvider.addPoolInformation(this);

    }
}
