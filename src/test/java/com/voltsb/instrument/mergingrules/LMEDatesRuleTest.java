package com.voltsb.instrument.mergingrules;

import com.voltsb.instrument.Instrument;
import com.voltsb.instrument.InstrumentDetails;
import com.voltsb.instrument.InstrumentDetailsImpl;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class LMEDatesRuleTest {
    @Test
    public void whenSourceIsLMEUseTradingAndDeliveryDates() {
        final LMEDatesRule rule = new LMEDatesRule();
        final Instrument lmeInstrument = new Instrument("LME", "PB_03_2018", new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true));
        final InstrumentDetailsImpl lastMergedDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "LME_PB",
                "Lead 13 March 2018",
                false);
        final InstrumentDetails result = rule.apply(lmeInstrument, lastMergedDetails);
        assertThat(result.getLastTradingDate(), equalTo(lmeInstrument.getDetails().getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeInstrument.getDetails().getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lastMergedDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lastMergedDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(lastMergedDetails.isTradable()));
    }

    @Test
    public void whenSourceIsNotLMEDontApplyRule() {
        final LMEDatesRule rule = new LMEDatesRule();
        final Instrument lmeInstrument = new Instrument("NOT_LME", "PB_03_2018", new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true));
        final InstrumentDetailsImpl lastMergedDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "LME_PB",
                "Lead 13 March 2018",
                false);
        final InstrumentDetails result = rule.apply(lmeInstrument, lastMergedDetails);
        assertThat(result.getLastTradingDate(), equalTo(lastMergedDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lastMergedDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lastMergedDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lastMergedDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(lastMergedDetails.isTradable()));
    }
}