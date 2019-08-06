package com.voltsb.instrument;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class InstrumentTest {
    @Test
    public void whenInstantiatedCheckAttributes() {
        final InstrumentDetailsImpl details = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        final Instrument instrument = new Instrument("LME", "PB_03_2018", details);
        assertThat(instrument.getSource(), equalTo("LME"));
        assertThat(instrument.getName(), equalTo("PB_03_2018"));
        assertThat(instrument.getDetails().getLastTradingDate(), equalTo(details.getLastTradingDate()));
        assertThat(instrument.getDetails().getDeliveryDate(), equalTo(details.getDeliveryDate()));
        assertThat(instrument.getDetails().getLabel(), equalTo(details.getLabel()));
        assertThat(instrument.getDetails().getMarket(), equalTo(details.getMarket()));
        assertThat(instrument.getDetails().isTradable(), equalTo(details.isTradable()));
    }

    @Test
    public void whenToStringCalledCheckContents() {
        final InstrumentDetailsImpl details = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        final String instString = new Instrument("LME", "PB_03_2018", details).toString();
        assertTrue(instString.contains("LME"));
        assertTrue(instString.contains("PB_03_2018"));
        assertTrue(instString.contains("LME_PB"));
        assertTrue(instString.contains("Lead 13 March 2018"));
        assertTrue(instString.contains("true"));
    }
}