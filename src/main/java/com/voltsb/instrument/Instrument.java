package com.voltsb.instrument;

/**
 * Immutable class representing an instrument.
 */
public final class Instrument {
    private final String source;
    private final String name;
    private final InstrumentDetails details;

    public Instrument(final String source, final String name, final InstrumentDetails details) {
        this.source = source;
        this.name = name;
        this.details = details;
    }

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public InstrumentDetails getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", details=" + details +
                '}';
    }
}