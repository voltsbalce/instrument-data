package com.voltsb.instrument;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Default implementation of {@link InstrumentDetails}.
 */
public final class InstrumentDetailsImpl implements InstrumentDetails {
    private final LocalDate lastTradingDate;
    private final LocalDate deliveryDate;
    private final String market;
    private final String label;
    private final boolean tradable;

    public InstrumentDetailsImpl(final LocalDate lastTradingDate,
                                 final LocalDate deliveryDate,
                                 final String market,
                                 final String label,
                                 final boolean tradable) {
        Objects.requireNonNull(lastTradingDate);
        Objects.requireNonNull(lastTradingDate);
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
        this.tradable = tradable;
    }

    @Override
    public LocalDate getLastTradingDate() {
        return lastTradingDate;
    }

    @Override
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public String getMarket() {
        return market;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isTradable() {
        return tradable;
    }

    @Override
    public String toString() {
        return "InstrumentDetailsImpl{" +
                "lastTradingDate=" + getLastTradingDate() +
                ", deliveryDate=" + getDeliveryDate() +
                ", market='" + getMarket() + '\'' +
                ", label='" + getLabel() + '\'' +
                ", tradable=" + isTradable() +
                '}';
    }
}