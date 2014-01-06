package org.feathercoin.monitoring;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.feathercoin.monitoring.dto.FtcBalanceResponse;
import org.feathercoin.monitoring.dto.FtcDifficultyResponse;
import org.feathercoin.monitoring.dto.FtcValueResponse;
import org.feathercoin.monitoring.json.rest.FTCJsonRequestExecutor;
import org.feathercoin.monitoring.util.ErrorCallback;
import org.feathercoin.monitoring.util.JSONCall;
import org.feathercoin.monitoring.util.SuccessCallback;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static org.feathercoin.monitoring.util.RepeatingJsonCallExecutor.executeJsonCall;

public class FtcValuePanel extends Panel implements Serializable {

    public static final int NUMBER_OF_REPETITIONS = 5;


    public FtcValuePanel(String id, final FTCJsonRequestExecutor ftcJsonRequestExecutor, final String ftcAddress) {
        super(id);

        addFtcToDollarValue(ftcJsonRequestExecutor);
        addFtcAddressAndTotalValueOrDefaultsIfAddressNotDefined(ftcJsonRequestExecutor, ftcAddress);
        addFtcDifficulty(ftcJsonRequestExecutor);
    }

    private void addFtcAddressAndTotalValueOrDefaultsIfAddressNotDefined(FTCJsonRequestExecutor ftcJsonRequestExecutor, String ftcAddress) {
        if (isFtcAddressDefined(ftcAddress)) {
            addFtcAddressAndTotalValue(ftcJsonRequestExecutor, ftcAddress);

        } else {
            setDefaultValues();
        }
    }

    private void addFtcDifficulty(final FTCJsonRequestExecutor ftcJsonRequestExecutor) {
        executeJsonCall(new JSONCall<FtcDifficultyResponse>(){
            @Override
            public FtcDifficultyResponse executeJSONCall() {
                return ftcJsonRequestExecutor.fetchCurrentDifficulty();
            }
        }, new SuccessCallback<FtcDifficultyResponse>() {
                            @Override
                            public void onSuccess(FtcDifficultyResponse result) {
                                add(new Label("ftcDifficulty", result.getDifficulty()));
                            }
                        }, new ErrorCallback() {
                            @Override
                            public void onError(List<Throwable> errors) {
                                add(new Label("ftcDifficulty").setVisible(false));
                            }
                        },NUMBER_OF_REPETITIONS);
    }

    private void setDefaultValues() {
        add(new Label("ftcBalance").setVisible(false));
        add(new Label("ftcAddress", "please define your FTC-Address"));
        add(new Label("ftcValueTotal").setVisible(false));
    }

    private boolean isFtcAddressDefined(String ftcAddress) {
        return ftcAddress != null && !ftcAddress.isEmpty();
    }

    private void addFtcAddressAndTotalValue(final FTCJsonRequestExecutor ftcJsonRequestExecutor, final String ftcAddress) {
        executeJsonCall(new JSONCall<FtcBalanceResponse>() {
                            @Override
                            public FtcBalanceResponse executeJSONCall() {
                                return ftcJsonRequestExecutor.fetchCurrentBalance(ftcAddress);
                            }
                        }, new SuccessCallback<FtcBalanceResponse>() {
                            @Override
                            public void onSuccess(final FtcBalanceResponse result) {
                                add(new Label("ftcBalance", result.getBalance()));
                                add(new Label("ftcAddress", ftcAddress));

                                executeJsonCall(new JSONCall<FtcValueResponse>() {
                                                    @Override
                                                    public FtcValueResponse executeJSONCall() {
                                                        return ftcJsonRequestExecutor.fetchCurrentUsdValue(result.getBalance());
                                                    }
                                                }, new SuccessCallback<FtcValueResponse>() {
                                                    @Override
                                                    public void onSuccess(FtcValueResponse result) {
                                                        add(new Label("ftcValueTotal", result.getUsdValue()));
                                                    }
                                                },
                                        new ErrorCallback() {
                                            @Override
                                            public void onError(List<Throwable> errors) {
                                                add(new Label("ftcValueTotal").setVisible(false));
                                            }
                                        }, NUMBER_OF_REPETITIONS
                                );
                            }
                        }, new ErrorCallback() {
                            @Override
                            public void onError(List<Throwable> errors) {
                                add(new Label("ftcBalance").setVisible(false));
                                add(new Label("ftcAddress", ftcAddress));
                                add(new Label("ftcValueTotal").setVisible(false));
                            }
                        }, NUMBER_OF_REPETITIONS
        );
    }

    private void addFtcToDollarValue(final FTCJsonRequestExecutor ftcJsonRequestExecutor) {
        executeJsonCall(new JSONCall<FtcValueResponse>() {
                            @Override
                            public FtcValueResponse executeJSONCall() {
                                return ftcJsonRequestExecutor.fetchCurrentUsdValue(BigDecimal.ONE);
                            }
                        }, new SuccessCallback<FtcValueResponse>() {
                            @Override
                            public void onSuccess(FtcValueResponse result) {
                                add(new Label("ftcValue", result.getUsdValue()));
                            }
                        },
                new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        add(new Label("ftcValue").setVisible(false));
                    }
                }, NUMBER_OF_REPETITIONS
        );
    }
}
