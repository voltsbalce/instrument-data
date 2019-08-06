package com.voltsb.instrument;

import com.voltsb.instrument.mergingrules.LMEDatesRule;
import com.voltsb.instrument.mergingrules.PRIMETradableRule;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Internal instrument rawDataMap container. Threadsafe.
 */
final class InstrumentData {

    // Map<mappingKey, Map<source, Instrument>>
    private final Map<String, Map<String, Instrument>> rawDataMap = new ConcurrentHashMap<>();

    // Map<mappingKey, Map<source, InstrumentDetails>>
    private final Map<String, Map<String, InstrumentDetails>> mergedDetailsMap = new ConcurrentHashMap<>();

    private final MergingRule[] rules;

    InstrumentData() {
        this(new LMEDatesRule(), new PRIMETradableRule());
    }

    InstrumentData(final MergingRule... rules) {
        this.rules = rules;
    }

    void add(final String source, final String mappingKey, final String name, final InstrumentDetails details) {
        // update raw data map with the new instrument
        final Map<String, Instrument> newRawDataBySource = new ConcurrentHashMap<>();
        final Map<String, Instrument> oldRawDataBySource = rawDataMap.putIfAbsent(mappingKey, newRawDataBySource);
        final Map<String, Instrument> rawDataBySource = oldRawDataBySource != null ? oldRawDataBySource : newRawDataBySource;
        rawDataBySource.put(source, new Instrument(source, name, details));

        // update all merged details - one instrument update may impact the merged details of any source
        final Map<String, InstrumentDetails> newMergedDetailsBySource = new ConcurrentHashMap<>();
        for(Instrument instrument: rawDataBySource.values()) {
            newMergedDetailsBySource.put(instrument.getSource(), merge(instrument, rawDataBySource, rules));
        }
        mergedDetailsMap.put(mappingKey, newMergedDetailsBySource);
    }

    private static InstrumentDetails merge(final Instrument instrument,
                                           final Map<String, Instrument> rawDataBySource,
                                           final MergingRule[] rules) {
        InstrumentDetails lastMergedDetails = instrument.getDetails();
        for (final Instrument otherInstrument : rawDataBySource.values()) {
            // skip instrument for target source since we already have it as the initial instrument for merging
            if (otherInstrument.getSource().equals(instrument.getSource())) continue;
            for (final MergingRule rule : rules) {
                lastMergedDetails = rule.apply(otherInstrument, lastMergedDetails);
            }
        }
        return lastMergedDetails;
    }

    Optional<InstrumentDetails> publish(final String source, final String mappingKey) {
        final Map<String, InstrumentDetails> mapBySource = mergedDetailsMap.get(mappingKey);
        if (mapBySource == null || mapBySource.isEmpty()) return Optional.empty(); // no instrument for that key
        return Optional.ofNullable(mapBySource.get(source));
    }
}
