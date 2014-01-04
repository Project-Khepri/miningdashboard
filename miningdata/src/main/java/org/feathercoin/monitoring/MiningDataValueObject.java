package org.feathercoin.monitoring;

import java.util.Date;

public class MiningDataValueObject {

    private Date id;

    public Date getId() {
        return id;
    }

    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MiningDataValueObject [id=" + id + ", value=" + value + "]";
    }

}
