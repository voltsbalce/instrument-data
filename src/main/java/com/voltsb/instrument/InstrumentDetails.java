package com.voltsb.instrument;

import java.time.LocalDate;

/**
 * Interface for getting instrument properties.
 */
public interface InstrumentDetails {
    LocalDate getLastTradingDate();

    LocalDate getDeliveryDate();

    String getMarket();

    String getLabel();

    boolean isTradable();
}
