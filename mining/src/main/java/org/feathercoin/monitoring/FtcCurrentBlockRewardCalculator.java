package org.feathercoin.monitoring;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FtcCurrentBlockRewardCalculator implements Serializable{
    private static final int BLOCKS_BEFORE_REWARD_IS_HALVED = 840000;
    private static final int STARTING_BLOCK_REWARD = 200;

    public BigDecimal calculateCurrentBlockReward(int currentBlock){
        int rounds = BigDecimal.valueOf(currentBlock).divide(BigDecimal.valueOf(BLOCKS_BEFORE_REWARD_IS_HALVED), 0, RoundingMode.FLOOR).intValue();
        BigDecimal result = BigDecimal.valueOf(STARTING_BLOCK_REWARD);
        for (int i=0;i<rounds;i++){
            result = result.divide(BigDecimal.valueOf(2),10,RoundingMode.HALF_UP);
        }
        return result.setScale(10,RoundingMode.HALF_UP);
    }

}
