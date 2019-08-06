package com.voltsb.instrument;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Instrument data container. Use this to store instrument data feeds and get instrument information.<br>
 * It has its own threading mechanism to handle requests for processing instrument data.<br>
 * This is a singleton so always use through the {@link InstrumentDataContainer#SINGLETON} instance.
 */
public final class InstrumentDataContainer {
    public static final InstrumentDataContainer SINGLETON = new InstrumentDataContainer();

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private final InstrumentData data = new InstrumentData();

    /**
     * Add instrument data to this container.
     *
     * @param source instrument source name such as "LME" or "PRIME"
     * @param mappingKey key that is common to instruments of any source, used for merging instrument data across
     *                   different sources
     * @param name name of the instrument
     * @param details instrument properties common to all instruments from any source
     * @throws ExecutionException thrown when there was an exception while trying to get the instrument data
     * @throws InterruptedException thrown when the thread is interrupted
     */
    public void add(final String source,
                    final String mappingKey,
                    final String name,
                    final InstrumentDetails details) throws ExecutionException, InterruptedException {
        executor.submit(() -> data.add(source, mappingKey, name, details)).get();
    }

    /**
     * Get a view of an instrument.
     *
     * @param source instrument source name such as "LME" or "PRIME"
     * @param mappingKey key that is common to instruments of any source
     * @return instrument properties or empty if not found
     * @throws ExecutionException thrown when there was an exception while trying to get the instrument data
     * @throws InterruptedException thrown when the thread is interrupted
     */
    public Optional<InstrumentDetails> publish(final String source, final String mappingKey) throws ExecutionException, InterruptedException {
        return executor.submit(() -> data.publish(source, mappingKey)).get();
    }

    /**
     * Call this when the application is shutting down to clean up all resources of this instrument container such as
     * executor services.
     */
    public void stop() {
        executor.shutdown();
    }

    /**
     * Test Run
     */
    public static void main(final String[] args) throws ExecutionException, InterruptedException {
        final InstrumentDetailsImpl lmeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 15),
                LocalDate.of(2018, 3, 17),
                "LME_PB",
                "Lead 13 March 2018",
                true);
        SINGLETON.add("LME", "PB_03_2018", "PB_03_2018", lmeDetails);

        System.out.println(SINGLETON.publish("LME", "PB_03_2018").map(Object::toString).orElse("not found"));

        final InstrumentDetailsImpl primeDetails = new InstrumentDetailsImpl(
                LocalDate.of(2018, 3, 14),
                LocalDate.of(2018, 3, 18),
                "LME_PB",
                "Lead 13 March 2018",
                false);
        SINGLETON.add("PRIME", "PB_03_2018", "PRIME_PB_03_2018", primeDetails);
        System.out.println(SINGLETON.publish("LME", "PB_03_2018").map(Object::toString).orElse("not found"));
        System.out.println(SINGLETON.publish("PRIME", "PB_03_2018").map(Object::toString).orElse("not found"));
        SINGLETON.stop();
    }
}
