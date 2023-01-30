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

    /**
     *  Converts an input value, scaled according to this range, to a value between 0.0 and 1.0
     */
    public double normalizeValue(
        final double value
    ) {
        return (value - getLowValue()) / _delta;
    }

    /**
     * Converts an input value linearly from 0.0 to 1.0 to a corresponding value within the range
     * such that an input of 0.0 produces an output of lowValue, while an input of 1.0 produces
     * highValue.
     */
    public double scaleValue(
        final double value
    ) {
        return (value * _delta) + getLowValue();
    }

    public double getDelta() { return _delta; }

    public boolean isInRange(
        final double value
    ) {
        return (value >= getLowValue()) && (value <= getHighValue());
    }
}
