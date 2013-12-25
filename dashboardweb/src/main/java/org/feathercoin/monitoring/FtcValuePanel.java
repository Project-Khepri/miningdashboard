package org.feathercoin.monitoring;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.feathercoin.monitoring.dto.FtcBalanceResponse;
import org.feathercoin.monitoring.dto.FtcDifficultyResponse;
import org.feathercoin.monitoring.dto.FtcValueResponse;
import org.feathercoin.monitoring.json.rest.FTCJsonRequestExecutor;

import java.io.Serializable;
import java.math.BigDecimal;

public class FtcValuePanel extends Panel implements Serializable{

    private FTCJsonRequestExecutor ftcJsonRequestExecutor;

    public FtcValuePanel(String id, FTCJsonRequestExecutor ftcJsonRequestExecutor, String ftcAddress){
        super(id);
        this.ftcJsonRequestExecutor = ftcJsonRequestExecutor;
        FtcValueResponse ftcValueResponse = ftcJsonRequestExecutor.fetchCurrentUsdValue(BigDecimal.ONE);
        add(new Label("ftcValue", ftcValueResponse.getUsdValue()));


        if (ftcAddress!=null && !ftcAddress.isEmpty()){
            FtcBalanceResponse ftcBalanceResponse = ftcJsonRequestExecutor.fetchCurrentBalance(ftcAddress);
            add(new Label("ftcBalance", ftcBalanceResponse.getBalance()));
            add(new Label("ftcAddress", ftcAddress));

            ftcValueResponse = ftcJsonRequestExecutor.fetchCurrentUsdValue(ftcBalanceResponse.getBalance());
            add(new Label("ftcValueTotal", ftcValueResponse.getUsdValue()));
        }else{
            add(new Label("ftcBalance").setVisible(false));
            add(new Label("ftcAddress", "please define your FTC-Address"));
            add(new Label("ftcValueTotal").setVisible(false));

        }

        FtcDifficultyResponse ftcDifficultyResponse = ftcJsonRequestExecutor.fetchCurrentDifficulty();
        add(new Label("ftcDifficulty", ftcDifficultyResponse.getDifficulty()));
    }
}
