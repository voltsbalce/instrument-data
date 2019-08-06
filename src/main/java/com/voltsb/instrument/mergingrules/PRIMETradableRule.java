package com.voltsb.instrument.mergingrules;

import com.voltsb.instrument.Instrument;
import com.voltsb.instrument.InstrumentDetails;
import com.voltsb.instrument.InstrumentDetailsDecorator;
import com.voltsb.instrument.MergingRule;

public class PRIMETradableRule implements MergingRule {

    @Override
    public InstrumentDetails apply(final Instrument instrument, final InstrumentDetails lastMergedDetails) {
        if(!instrument.getSource().equals("PRIME")) return lastMergedDetails;
        return new InstrumentDetailsDecorator(lastMergedDetails)
        {
            @Override
            public boolean isTradable() {
                return instrument.getDetails().isTradable();
            }
        };
    }
}
