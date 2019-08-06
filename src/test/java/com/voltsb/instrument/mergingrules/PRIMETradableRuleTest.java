package com.voltsb.instrument.mergingrules;

import com.voltsb.instrument.Instrument;
import com.voltsb.instrument.InstrumentDetails;
import com.voltsb.instrument.InstrumentDetailsImpl;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class PRIMETradableRuleTest {
    @Test
    public void whenSourceIsPRIMEMergeTradableProperty() {
        final PRIMETradableRule rule = new PRIMETradableRule();
        final Instrument primeInstrument = new Instrument("PRIME", "PRIME_PB_03_2018", new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "LME_PB",
                "Lead 13 March 2018",
                false));
        final InstrumentDetailsImpl lastMergedDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        final InstrumentDetails result = rule.apply(primeInstrument, lastMergedDetails);
        assertThat(result.getLastTradingDate(), equalTo(lastMergedDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lastMergedDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lastMergedDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lastMergedDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeInstrument.getDetails().isTradable()));
    }

    @Test
    public void whenSourceIsNotPRIMEDontApplyRules() {
        final PRIMETradableRule rule = new PRIMETradableRule();
        final Instrument primeInstrument = new Instrument("NOT_PRIME", "PRIME_PB_03_2018", new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "LME_PB",
                "Lead 13 March 2018",
                false));
        final InstrumentDetailsImpl lastMergedDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        final InstrumentDetails result = rule.apply(primeInstrument, lastMergedDetails);
        assertThat(result.getLastTradingDate(), equalTo(lastMergedDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lastMergedDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lastMergedDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lastMergedDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(lastMergedDetails.isTradable()));
    }
}