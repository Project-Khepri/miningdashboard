package org.feathercoin.monitoring;


import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.feathercoin.monitoring.beans.PoolInfoBean;
import org.feathercoin.monitoring.config.FtcConfiguration;
import org.feathercoin.monitoring.config.MinerStatsConfiguration;
import org.feathercoin.monitoring.dto.FtcBalanceResponse;
import org.feathercoin.monitoring.dto.FtcDifficultyResponse;
import org.feathercoin.monitoring.dto.FtcStatsResponse;
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
    @SpringBean private ProfitabilityCalculator profitabilityCalculator;
    @SpringBean private FtcCurrentBlockRewardCalculator ftcCurrentBlockRewardCalculator;
    @SpringBean private MinerStatsConfiguration minerStatsConfiguration;
    @SpringBean private PoolDataFetcher poolDataFetcher;
    @SpringBean private PoolTotalSummaryCalculator poolTotalSummaryCalculator;
    @SpringBean private FTCJsonRequestExecutor ftcJsonRequestExecutor;
    @SpringBean private FtcConfiguration ftcConfiguration;


    public FtcValuePanel(String id) {
        super(id);
        Injector.get().inject(this);
        BigDecimal dollarValue = addFtcToDollarValue(ftcJsonRequestExecutor);
        addFtcDifficulty(ftcJsonRequestExecutor);
        addFtcAddressAndTotalValueOrDefaultsIfAddressNotDefined(ftcJsonRequestExecutor, ftcConfiguration.getFtcAddress());

        if (dollarValue!=null)
            addFtcProfitability(ftcJsonRequestExecutor,dollarValue);
        else
            provideEmptyProfitabilityStats();
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

    private BigDecimal addFtcToDollarValue(final FTCJsonRequestExecutor ftcJsonRequestExecutor) {
        final BigDecimal[] dollarValue = {null};
        executeJsonCall(new JSONCall<FtcValueResponse>() {
                            @Override
                            public FtcValueResponse executeJSONCall() {
                                return ftcJsonRequestExecutor.fetchCurrentUsdValue(BigDecimal.ONE);
                            }
                        }, new SuccessCallback<FtcValueResponse>() {
                            @Override
                            public void onSuccess(FtcValueResponse result) {
                                add(new Label("ftcValue", result.getUsdValue()));
                                dollarValue[0]= result.getUsdValue();
                            }
                        },
                new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        add(new Label("ftcValue").setVisible(false));
                    }
                }, NUMBER_OF_REPETITIONS
        );
        return dollarValue[0];
    }

    private void addFtcProfitability(final FTCJsonRequestExecutor ftcJsonRequestExecutor, final BigDecimal dollarValue) {
        executeJsonCall(new JSONCall<FtcStatsResponse>() {
                            @Override
                            public FtcStatsResponse executeJSONCall() {
                                return ftcJsonRequestExecutor.fetchFtcStatistics();
                            }
                        }, new SuccessCallback<FtcStatsResponse>() {
                            @Override
                            public void onSuccess(FtcStatsResponse result) {
                                List<PoolInfoBean> poolInfoBeans = poolDataFetcher.fetchPoolInformation();
                                PoolInfoBean totals = poolTotalSummaryCalculator.calculateTotal(poolInfoBeans);
                                profitabilityCalculator.setHashRate(BigDecimal.valueOf(totals.getTotalHashrate()));
                                profitabilityCalculator.setFeathercoinsPerBlock(
                                        ftcCurrentBlockRewardCalculator.calculateCurrentBlockReward(result.getCurrentBlock()));
                                profitabilityCalculator.setPowerConsumptionInWatts(
                                        BigDecimal.valueOf(minerStatsConfiguration.getPowerConsumptionInWatts()));
                                profitabilityCalculator.setMiningHardwareCosts(
                                        minerStatsConfiguration.getHardwareCosts()
                                );
                                profitabilityCalculator.setConversionRate(dollarValue);
                                profitabilityCalculator.setDifficulty(result.getCurrentDiff());
                                profitabilityCalculator.setElectricityRate(minerStatsConfiguration.getElectricityRate());

                                BigDecimal coinsPerDay = profitabilityCalculator.calculateCoinsPerDay();
                                BigDecimal NetRevenuePerDay = profitabilityCalculator.calculateNetRevenuePerDay();
                                BigDecimal powerCostsPerDay = profitabilityCalculator.calculatePowerCostsPerDay();
                                BigDecimal revenuePerDay = profitabilityCalculator.calculateRevenuePerDay();
                                BigDecimal breakEvenInDays = profitabilityCalculator.calculateHardwareBreakEvenInDays();
                                add(new Label("ftcCoinsPerDay", coinsPerDay));
                                add(new Label("ftcNetRevenuePerDay", NetRevenuePerDay));
                                add(new Label("ftcPowerCostsPerDay", powerCostsPerDay));
                                add(new Label("ftcRevenuePerDay", revenuePerDay));
                                add(new Label("ftcHardwareBreakEvenInDays", breakEvenInDays));
                                add(new Label("ftcPowerConsumption", minerStatsConfiguration.getPowerConsumptionInWatts()));
                                add(new Label("ftcElectricityRate", minerStatsConfiguration.getElectricityRate()));
                                add(new Label("ftcHardwareCosts", minerStatsConfiguration.getHardwareCosts()));
                            }
                        },
                new ErrorCallback() {
                    @Override
                    public void onError(List<Throwable> errors) {
                        provideEmptyProfitabilityStats();
                    }
                }, NUMBER_OF_REPETITIONS
        );
    }

    private void provideEmptyProfitabilityStats() {
        add(new Label("ftcCoinsPerDay").setVisible(false));
        add(new Label("ftcNetRevenuePerDay").setVisible(false));
        add(new Label("ftcPowerCostsPerDay").setVisible(false));
        add(new Label("ftcRevenuePerDay").setVisible(false));
        add(new Label("ftcHardwareBreakEvenInDays").setVisible(false));
        add(new Label("ftcPowerConsumption").setVisible(false));
        add(new Label("ftcElectricityRate").setVisible(false));
        add(new Label("ftcHardwareCosts").setVisible(false));
    }
}
