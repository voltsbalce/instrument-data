package com.voltsb.instrument;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class InstrumentDataTest {
    @Test
    public void whenUpdateOnLMEOnlyPublishDetailsAsIs() {
        final InstrumentData data = new InstrumentData();
        final InstrumentDetailsImpl lmeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        data.add("LME", "PB_03_2018", "PB_03_2018", lmeDetails);
        final InstrumentDetails result = data.publish("LME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lmeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lmeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(lmeDetails.isTradable()));
    }

    @Test
    public void whenUpdatesOnLMEAndPrimePublishBasedOnGivenRules() {
        final InstrumentData data = new InstrumentData();
        final InstrumentDetailsImpl lmeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        data.add("LME", "PB_03_2018", "PB_03_2018", lmeDetails);
        final InstrumentDetailsImpl primeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "(PRIME) LME_PB",
                "(PRIME) Lead 13 March 2018",
                false);
        data.add("PRIME", "PB_03_2018", "PRIME_PB_03_2018", primeDetails);

        InstrumentDetails result = data.publish("LME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lmeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lmeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));

        result = data.publish("PRIME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(primeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(primeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));
    }

    @Test
    public void whenUpdatesOnLMEPrimeAndLMEPublishBasedOnGivenRules() {
        final InstrumentData data = new InstrumentData();
        final InstrumentDetailsImpl lmeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        data.add("LME", "PB_03_2018", "PB_03_2018", lmeDetails);
        final InstrumentDetailsImpl primeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "(PRIME) LME_PB",
                "(PRIME) Lead 13 March 2018",
                false);
        data.add("PRIME", "PB_03_2018", "PRIME_PB_03_2018", primeDetails);

        InstrumentDetails result = data.publish("LME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lmeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(lmeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));

        result = data.publish("PRIME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(primeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(primeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));

        final InstrumentDetailsImpl lmeDetails2 = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 13),
                LocalDate.of(2018, 3, 19),
                "(LME 2) LME_PB",
                "(LME 2) Lead 13 March 2018",
                false);
        data.add("LME", "PB_03_2018", "PB_03_2018", lmeDetails2);
        result = data.publish("LME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails2.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails2.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(lmeDetails2.getLabel()));
        assertThat(result.getMarket(), equalTo(lmeDetails2.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));

        result = data.publish("PRIME", "PB_03_2018").get();
        assertThat(result.getLastTradingDate(), equalTo(lmeDetails2.getLastTradingDate()));
        assertThat(result.getDeliveryDate(), equalTo(lmeDetails2.getDeliveryDate()));
        assertThat(result.getLabel(), equalTo(primeDetails.getLabel()));
        assertThat(result.getMarket(), equalTo(primeDetails.getMarket()));
        assertThat(result.isTradable(), equalTo(primeDetails.isTradable()));
    }

}