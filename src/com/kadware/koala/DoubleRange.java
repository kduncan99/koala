/*
 * Koala - Virtual Modular Synthesizer
 * Copyright (c) 2023 by Kurt Duncan - All Rights Reserved
 */

package com.kadware.koala;

public class DoubleRange extends Range<Double> {

    private final double _delta;

    public DoubleRange(
        final Double lowValue,
        final Double highValue
    ) {
        super(lowValue, highValue);
        _delta = highValue - lowValue;
    }

    public double clipValue(
        final double value
    ) {
        return Math.max(Math.min(value, getHighValue()), getLowValue());
    }

    public double normalizeValue(
        final double value
    ) {
        return (value - getLowValue()) / _delta;
    }

    public double getDelta() { return _delta; }

    public boolean isInRange(
        final double value
    ) {
        return (value >= getLowValue()) && (value <= getHighValue());
    }
}
