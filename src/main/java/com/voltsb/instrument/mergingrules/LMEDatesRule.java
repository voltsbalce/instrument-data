package com.voltsb.instrument.mergingrules;

import com.voltsb.instrument.Instrument;
import com.voltsb.instrument.InstrumentDetails;
import com.voltsb.instrument.InstrumentDetailsDecorator;
import com.voltsb.instrument.MergingRule;

import java.time.LocalDate;

public final class LMEDatesRule implements MergingRule {
    @Override
    public InstrumentDetails apply(final Instrument instrument, final InstrumentDetails lastMergedDetails) {
        if(!instrument.getSource().equals("LME")) return lastMergedDetails;
        return new InstrumentDetailsDecorator(lastMergedDetails)
        {
            @Override
            public LocalDate getLastTradingDate() {
                return instrument.getDetails().getLastTradingDate();
            }

            @Override
            public LocalDate getDeliveryDate() {
                return instrument.getDetails().getDeliveryDate();
            }
        };
    }
}
