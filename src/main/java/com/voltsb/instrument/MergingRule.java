package com.voltsb.instrument;

/**
 * Interface for implementing a rule for merging instrument data.
 */
@FunctionalInterface
public interface MergingRule {
    /**
     * Apply the merge rule.
     * @param instrument instrument to merge from
     * @param lastMergedDetails Instrument details to merge into. This contains what have been merged so far.
     * @return instrument details resulting from the merge of the instrument and last merged details.
     */
    InstrumentDetails apply(Instrument instrument, InstrumentDetails lastMergedDetails);
}
