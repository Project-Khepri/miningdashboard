package org.feathercoin.monitoring.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoinResponse extends CGMinerReponse {
    @SerializedName("COIN") private ArrayList<Coin> coin = new ArrayList<Coin>();
    public Coin getFirstCoin(){
        if (!coin.isEmpty())
            return coin.get(0);
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " CoinResponse{" +
                "coin=" + coin +
                '}';
    }
}
