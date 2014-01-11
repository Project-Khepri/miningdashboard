package org.feathercoin.monitoring;


import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;

public class FtcPoolsPanel extends Panel implements Serializable{
    @SpringBean private PoolInformationProvider poolInformationProvider;


    public FtcPoolsPanel(String id){
        super(id);
        Injector.get().inject(this);
        poolInformationProvider.addPoolInformation(this);
    }
}
