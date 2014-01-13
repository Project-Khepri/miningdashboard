package org.feathercoin.monitoring;

import java.util.Date;

public class StatsValueObject {

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
        return "StatsValueObject{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
