package com.voltsb.instrument;

import java.time.LocalDate;

public class InstrumentDetailsDecorator implements InstrumentDetails {
    private final InstrumentDetails inner;

    public InstrumentDetailsDecorator(InstrumentDetails inner) {
        this.inner = inner;
    }

    @Override
    public LocalDate getLastTradingDate() {
        return inner.getLastTradingDate();
    }

    @Override
    public LocalDate getDeliveryDate() {
        return inner.getDeliveryDate();
    }

    @Override
    public String getMarket() {
        return inner.getMarket();
    }

    @Override
    public String getLabel() {
        return inner.getLabel();
    }

    @Override
    public boolean isTradable() {
        return inner.isTradable();
    }

    @Override
    public String toString() {
        return "InstrumentDetailsDecorator{" +
                "lastTradingDate=" + getLastTradingDate() +
                ", deliveryDate=" + getDeliveryDate() +
                ", market='" + getMarket() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", tradable=" + isTradable() +
                '}';
    }
}
