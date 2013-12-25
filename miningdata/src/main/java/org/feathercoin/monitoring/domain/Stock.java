package org.feathercoin.monitoring.domain;

import java.math.BigDecimal;

public class Stock {
    private String id;
    private BigDecimal value;

    public Stock() {
    }

    public Stock(String id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
